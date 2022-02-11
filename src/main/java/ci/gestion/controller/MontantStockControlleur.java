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

import ci.gestion.entites.entreprise.MontantStock;
import ci.gestion.metier.MontantStockMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class MontantStockControlleur {
	@Autowired
	private MontantStockMetier montantStockMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<MontantStock> getCategoriesById(Long id) {
		MontantStock mt = null;

		try {
			mt = montantStockMetier.findById(id);
			if (mt == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le mt n'existe pas", id));
				new Reponse<MontantStock>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<MontantStock>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<MontantStock>(0, null, mt);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/montantStock")
	public String creer(@RequestBody MontantStock categorie) throws JsonProcessingException {
		Reponse<MontantStock> reponse;
		System.out.println(categorie);
		try {

			MontantStock cat = montantStockMetier.creer(categorie);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cat.getId()));
			reponse = new Reponse<MontantStock>(0, messages, cat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<MontantStock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/montantStock")
	public String update(@RequestBody MontantStock modif) throws JsonProcessingException {

		Reponse<MontantStock> reponse = null;
		Reponse<MontantStock> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getCategoriesById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				MontantStock categories = montantStockMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", categories.getId()));
				reponse = new Reponse<MontantStock>(0, messages, categories);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<MontantStock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("La catégories n'existe pas"));
			reponse = new Reponse<MontantStock>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	//////// recuperer une categorie par son id
	@GetMapping("/montantStock/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<MontantStock> reponse = null;

		reponse = getCategoriesById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour cette catégorie");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// get all categories
	@GetMapping("/montantStock")
	public String findAll() throws JsonProcessingException {
		Reponse<List<MontantStock>> reponse;
		try {
			List<MontantStock> categories = montantStockMetier.findAll();
			if (!categories.isEmpty()) {
				reponse = new Reponse<List<MontantStock>>(0, null, categories);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<MontantStock>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer une categorie
	@DeleteMapping("/montantStock/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, montantStockMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/getMontantStockByidEntreprise/{id}")
	public String getMontantStockByEntreprise(@PathVariable Long id) throws JsonProcessingException {
		Reponse<MontantStock> reponse;
		try {
			MontantStock pers = montantStockMetier.getMontantStockByIdEntreprise(id);
			
				reponse = new Reponse<MontantStock>(0, null, pers);
			
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
