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
import ci.gestion.metier.achatTravaux.IAchatTravauxMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AchatTravauxController {
	@Autowired
	private IAchatTravauxMetier achatTravauxMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;

// recuper travaux par identifiant
	private Reponse<AchatTravaux> getAchatTravauxById(Long id) {
		AchatTravaux achat = null;

		try {
			achat = achatTravauxMetier.findById(id);
			if (achat == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("achat n'existe pas", id));
				new Reponse<AchatTravaux>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<AchatTravaux>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<AchatTravaux>(0, null, achat);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un Achat  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/achat")
	public String creer(@RequestBody AchatTravaux achat) throws JsonProcessingException {
		Reponse<AchatTravaux> reponse;
       
		try {

			AchatTravaux t1 = achatTravauxMetier.creer(achat);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getLibelle()));
			reponse = new Reponse<AchatTravaux>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<AchatTravaux>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@DeleteMapping("/DeleteDetail/{idAchat}/{idDetail}")
	public String suppDetail(@PathVariable Long idAchat, @PathVariable Long idDetail) throws JsonProcessingException {
		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, achatTravauxMetier.supprimerDetailAchat(idAchat, idDetail));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	
	}
	
	@PutMapping("/achat")
	public String update(@RequestBody AchatTravaux  modif) throws JsonProcessingException {

		Reponse<AchatTravaux> reponse = null;
		Reponse<AchatTravaux> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getAchatTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				AchatTravaux achatTravaux = achatTravauxMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", achatTravaux.getId()));
				reponse = new Reponse<AchatTravaux>(0, messages, achatTravaux);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<AchatTravaux>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("AchatTravaux n'existe pas"));
			reponse = new Reponse<AchatTravaux>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer achat par id travaux
		@GetMapping("/achat/{idTravaux}")
		public String getAchatByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
			Reponse<List<AchatTravaux>> reponse;

			try {
				List<AchatTravaux> achats = achatTravauxMetier.findAchatByIdTravaux(idTravaux);
				if (!achats.isEmpty()) {
					reponse = new Reponse<List<AchatTravaux>>(0, null, achats);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas d'achat enregistré");
					reponse = new Reponse<List<AchatTravaux>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {

				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir un achat par son identifiant
		@GetMapping("/achats/{id}")
		public String getAchatById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<AchatTravaux> reponse;
			try {
				AchatTravaux db = achatTravauxMetier.findById(id);
				reponse = new Reponse<AchatTravaux>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer un achat
		@DeleteMapping("/achat/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, achatTravauxMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}
 
			return jsonMapper.writeValueAsString(reponse);
		}
}
 