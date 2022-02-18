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

import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.operation.Categorie;
import ci.gestion.metier.combo.CategorieMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategorieController {
	@Autowired
	private CategorieMetier categorieMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<Categorie> getCategoriesById(Long id) {
		Categorie categories = null;

		try {
			categories = categorieMetier.findById(id);
			if (categories == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("La catégorie n'existe pas", id));
				new Reponse<Categorie>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Categorie>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Categorie>(0, null, categories);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/categorie")
	public String creer(@RequestBody Categorie categorie) throws JsonProcessingException {
		Reponse<Categorie> reponse;
		System.out.println(categorie);
		try {

			Categorie cat = categorieMetier.creer(categorie);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cat.getLibelle()));
			reponse = new Reponse<Categorie>(0, messages, cat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Categorie>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/categorie")
	public String update(@RequestBody Categorie modif) throws JsonProcessingException {

		Reponse<Categorie> reponse = null;
		Reponse<Categorie> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getCategoriesById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
			    System.out.println("modif recupere2:" + modif);
				Categorie categories = categorieMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", categories.getId()));
				reponse = new Reponse<Categorie>(0, messages, categories);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Categorie>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La catégories n'existe pas"));
			reponse = new Reponse<Categorie>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	//////// recuperer une categorie par son id
	@GetMapping("/categorie/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Categorie> reponse = null;

		reponse = getCategoriesById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour cette catégorie");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// get all categories
	@GetMapping("/categorie")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Categorie>> reponse;
		try {
			List<Categorie> categories = categorieMetier.findAll();
			if (!categories.isEmpty()) {
				reponse = new Reponse<List<Categorie>>(0, null, categories);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Categorie>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer une categorie
	@DeleteMapping("/categorie/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, categorieMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/getCategorieByidEntreprise/{id}")
	public String getDepByEntreprise(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<Categorie>> reponse;
		try {
			List<Categorie> pers = categorieMetier.getCategorieByIdEntreprise(id);
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Categorie>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<Categorie>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
