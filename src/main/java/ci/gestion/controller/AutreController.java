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

import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.metier.autres.IAutresMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AutreController {
	@Autowired
	private IAutresMetier autresMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper mainOeuvre par identifiant
	private Reponse<Autres> getAutresTravauxById(Long id) {
		Autres autres = null;

		try {
			autres = autresMetier.findById(id);
			if (autres == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("autres n'existe pas", id));
				new Reponse<Autres>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Autres>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Autres>(0, null, autres);
	}
////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer  autres  dans la base
///////////////////////////////////////////////////////////////////////////

	@PostMapping("/autres")
	public String creer(@RequestBody Autres autres) throws JsonProcessingException {
		Reponse<Autres> reponse;
        System.out.println(autres);
		try {
			Autres t1 = autresMetier.creer(autres);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Autres>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Autres>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/autres")
	public String update(@RequestBody Autres  modif) throws JsonProcessingException {

		Reponse<Autres> reponse = null;
		Reponse<Autres> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getAutresTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Autres achatTravaux = autresMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", achatTravaux.getId()));
				reponse = new Reponse<Autres>(0, messages, achatTravaux);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Autres>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Autres n'existe pas"));
			reponse = new Reponse<Autres>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer Autres par id travaux
		@GetMapping("/autres/{idTravaux}")
		public String getAutreByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
			Reponse<List<Autres>> reponse;

			try {
				List<Autres> autres = autresMetier.findAutresByIdTravaux(idTravaux);
				if (!autres.isEmpty()) {
					reponse = new Reponse<List<Autres>>(0, null, autres);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas Autres enregistré");
					reponse = new Reponse<List<Autres>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {

				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir autres par son identifiant
		@GetMapping("/autre/{id}")
		public String getAutresById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Autres> reponse;
			try {
				Autres db = autresMetier.findById(id);
				reponse = new Reponse<Autres>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer autre
		@DeleteMapping("/autres/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, autresMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
		@DeleteMapping("/DeleteDetailAutre/{idAutre}/{idDetail}")
		public String suppDetail(@PathVariable Long idAutre, @PathVariable Long idDetail) throws JsonProcessingException {
			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, autresMetier.supprimerDetailAutre(idAutre, idDetail));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		
		}
		
}
