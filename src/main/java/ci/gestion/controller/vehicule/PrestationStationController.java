package ci.gestion.controller.vehicule;

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

import ci.gestion.entites.vehicule.PrestationStation;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;
import ci.gestion.metier.vehicule.PrestationStatioMetier;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PrestationStationController {
	@Autowired
	private PrestationStatioMetier prestationStatioMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper vehiculeMetier par identifiant
	private Reponse<PrestationStation> getPrestationStationById(Long id) {
		PrestationStation carburant = null;

		try {
			carburant = prestationStatioMetier.findById(id);
			if (carburant == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("l'élément n'existe pas", id));
				new Reponse<PrestationStation>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<PrestationStation>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<PrestationStation>(0, null, carburant);
	}
//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un Carburant dans  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/prestationStation")
	public String creer(@RequestBody PrestationStation carburant) throws JsonProcessingException {
		Reponse<PrestationStation> reponse;

		try {
			PrestationStation v = prestationStatioMetier.creer(carburant);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", v.getId()));
			reponse = new Reponse<PrestationStation>(0, messages, v);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<PrestationStation>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
/////////////////////////////////////////////////////////////////////////////////////////
// modifier une banque dans la base de donnee
///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/prestationStation")
	public String modifier(@RequestBody PrestationStation modif) throws JsonProcessingException {
		Reponse<PrestationStation> reponsePersModif = null;
		Reponse<PrestationStation> reponse = null;

// on recupere le flash info a modifier
		reponsePersModif = getPrestationStationById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				PrestationStation t2 = prestationStatioMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", t2.getId()));
				reponse = new Reponse<PrestationStation>(0, messages, t2);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<PrestationStation>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La prestationStation n'existe pas"));
			reponse = new Reponse<PrestationStation>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

///////////////////////////////////////////////////////////////////////////////////////////////
// recuperer  Carburant  de la base de
/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/prestationStation")
	public String findAllCarburant() throws JsonProcessingException, InvalideOryzException {
		Reponse<List<PrestationStation>> reponse;
		try {
			List<PrestationStation> flashs = prestationStatioMetier.findAll();

			if (!flashs.isEmpty()) {
				reponse = new Reponse<List<PrestationStation>>(0, null, flashs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de prestationStation info enregistrées");
				reponse = new Reponse<List<PrestationStation>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<PrestationStation>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
	@GetMapping("/prestationStation/{id}")
	public String chercherBanqueById(@PathVariable Long id) throws JsonProcessingException {
// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<PrestationStation> reponse = null;

		reponse = getPrestationStationById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour cette prestationStation");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

// supprimer un achat
	@DeleteMapping("/prestationStation/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, prestationStatioMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

}
