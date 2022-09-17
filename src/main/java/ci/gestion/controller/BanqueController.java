package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.banque.Banque;
import ci.gestion.metier.banque.IBanqueMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BanqueController {
	
	@Autowired
	private IBanqueMetier banqueMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuper banque par identifiant
		private Reponse<Banque> getBanqueById(Long id) {
			Banque banque = null;

			try {
				banque = banqueMetier.findById(id);
				if (banque == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("la banque n'existe pas", id));
					new Reponse<Banque>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Banque>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Banque>(0, null, banque);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer une banque dans  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/banque")
		public String creer(@RequestBody Banque banque) throws JsonProcessingException {
			Reponse<Banque> reponse;

			try {
                Banque t1 = banqueMetier.creer(banque);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", t1.getNom()));
				reponse = new Reponse<Banque>(0, messages, t1);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Banque>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier une banque dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

		@PutMapping("/banque")
		public String modifier(@RequestBody Banque modif) throws JsonProcessingException {
			Reponse<Banque> reponsePersModif = null;
			Reponse<Banque> reponse = null;

			// on recupere le flash info a modifier
			reponsePersModif = getBanqueById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					Banque t2 = banqueMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", t2.getNom()));
					reponse = new Reponse<Banque>(0, messages, t2);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<Banque>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("La banque n'existe pas"));
				reponse = new Reponse<Banque>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer toutes les banque  de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/banque")
		public String findAllBanque() throws JsonProcessingException, InvalideOryzException {
			Reponse<List<Banque>> reponse;
			try {
				List<Banque> flashs = banqueMetier.findAll();

				if (!flashs.isEmpty()) {
					reponse = new Reponse<List<Banque>>(0, null, flashs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de travail info enregistrées");
					reponse = new Reponse<List<Banque>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<List<Banque>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	////////recuperer travail  par son id
		@GetMapping("/banque/{id}")
		public String chercherBanqueById(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<Banque> reponse = null;

			reponse = getBanqueById(id);
			if (reponse.getBody() == null) {
				throw new RuntimeException("pas d'enregistrement pour cette banque");
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer un achat
				@DeleteMapping("/banque/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						reponse = new Reponse<Boolean>(0, null, banqueMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
		 
					return jsonMapper.writeValueAsString(reponse);
				}

}
