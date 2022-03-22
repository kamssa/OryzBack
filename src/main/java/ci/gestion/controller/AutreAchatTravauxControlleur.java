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

import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.AutreAchatTravaux;
import ci.gestion.metier.AutreAchatTravauxMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AutreAchatTravauxControlleur {
	@Autowired
	private AutreAchatTravauxMetier autreAchatTravauxMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper mainOeuvre par identifiant
		private Reponse<AutreAchatTravaux> getAutresTravauxById(Long id) {
			AutreAchatTravaux autres = null;

			try {
				autres = autreAchatTravauxMetier.findById(id);
				if (autres == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("autres n'existe pas", id));
					new Reponse<AutreAchatTravaux>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<AutreAchatTravaux>(0, null, autres);
		}
	////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer  autres  dans la base
	///////////////////////////////////////////////////////////////////////////

		@PostMapping("/autreAchatTravaux")
		public String creer(@RequestBody AutreAchatTravaux autres) throws JsonProcessingException {
			Reponse<AutreAchatTravaux> reponse;
	     
			try {
				AutreAchatTravaux t1 = autreAchatTravauxMetier.creer(autres);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", t1.getId()));
				reponse = new Reponse<AutreAchatTravaux>(0, messages, t1);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		@PutMapping("/autreAchatTravaux")
		public String update(@RequestBody AutreAchatTravaux  modif) throws JsonProcessingException {

			Reponse<AutreAchatTravaux> reponse = null;
			Reponse<AutreAchatTravaux> reponsePersModif = null;
			// on recupere autre a modifier
			System.out.println("modif recupere1:"+ modif);
			reponsePersModif = getAutresTravauxById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					System.out.println("modif recupere2:"+ modif);
					AutreAchatTravaux achatTravaux = autreAchatTravauxMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", achatTravaux.getId()));
					reponse = new Reponse<AutreAchatTravaux>(0, messages, achatTravaux);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Autres n'existe pas"));
				reponse = new Reponse<AutreAchatTravaux>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		
			// obtenir autres par son identifiant
			@GetMapping("/autreAchatTravaux/{id}")
			public String getAutreAchatsById(@PathVariable Long id) throws JsonProcessingException {
				Reponse<AutreAchatTravaux> reponse;
				try {
					AutreAchatTravaux db = autreAchatTravauxMetier.findById(id);
					reponse = new Reponse<AutreAchatTravaux>(0, null, db);
				} catch (Exception e) {
					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);

			}
			// supprimer autre
			@DeleteMapping("/autreAchatTravaux/{id}")
			public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;

				try {

					reponse = new Reponse<Boolean>(0, null, autreAchatTravauxMetier.supprimer(id));

				} catch (RuntimeException e1) {
					reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
				}

				return jsonMapper.writeValueAsString(reponse);
			}
			// recuperer achat par id travaux
			@GetMapping("/autreAchat/{idTravaux}")
			public String getAchatByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
				Reponse<List<AutreAchatTravaux>> reponse;

				try {
					List<AutreAchatTravaux> achats = autreAchatTravauxMetier.getAutreAchatTravauxTravauxByIdTravaux(idTravaux);
					if (!achats.isEmpty()) {
						reponse = new Reponse<List<AutreAchatTravaux>>(0, null, achats);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas d'achat enregistré");
						reponse = new Reponse<List<AutreAchatTravaux>>(1, messages, new ArrayList<>());
					}

				} catch (Exception e) {

					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
			}
}
