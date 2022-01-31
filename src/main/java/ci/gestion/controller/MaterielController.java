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

import ci.gestion.entites.combo.Categorie;
import ci.gestion.entites.combo.Materiel;
import ci.gestion.metier.combo.CategorieMetier;
import ci.gestion.metier.combo.MaterielMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MaterielController {
	@Autowired
	private MaterielMetier materielMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<Materiel> getMaterielById(Long id) {
		Materiel materiel = null;

		try {
			materiel = materielMetier.findById(id);
			if (materiel == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le materiel n'existe pas", id));
				new Reponse<Materiel>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Materiel>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Materiel>(0, null, materiel);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/materiel")
	public String creer(@RequestBody Materiel materiel) throws JsonProcessingException {
		Reponse<Materiel> reponse;
		System.out.println(materiel);
		try {

			Materiel mat = materielMetier.creer(materiel);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", mat.getLibelle()));
			reponse = new Reponse<Materiel>(0, messages, mat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Materiel>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/materiel")
	public String update(@RequestBody Materiel modif) throws JsonProcessingException {

		Reponse<Materiel> reponse = null;
		Reponse<Materiel> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getMaterielById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				Materiel materiels = materielMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", materiels.getId()));
				reponse = new Reponse<Materiel>(0, messages, materiels);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Materiel>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("L materiel n'existe pas"));
			reponse = new Reponse<Materiel>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	//////// recuperer une categorie par son id
	@GetMapping("/materiel/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Materiel> reponse = null;

		reponse = getMaterielById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour ce materiel");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// get all categories
	@GetMapping("/materiel")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Materiel>> reponse;
		try {
			List<Materiel> materiels = materielMetier.findAll();
			if (!materiels.isEmpty()) {
				reponse = new Reponse<List<Materiel>>(0, null, materiels);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de materiels enregistrées");
				reponse = new Reponse<List<Materiel>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer une categorie
	@DeleteMapping("/materiel/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, materielMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/getMaterielByidCategorie/{id}")
	public String getMatByCategorie(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<Materiel>> reponse;
		try {
			List<Materiel> pers = materielMetier.getMaterielByIdCategorie(id);
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Materiel>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<Materiel>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}
