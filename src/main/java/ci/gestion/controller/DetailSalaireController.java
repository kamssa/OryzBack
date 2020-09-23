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

import ci.gestion.entites.operation.Employe;
import ci.gestion.entites.salaire.DetailSalaire;
import ci.gestion.metier.IDetailSalaireMetier;
import ci.gestion.metier.IEmployeMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DetailSalaireController {
	@Autowired
	private IDetailSalaireMetier salaireMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper salaire par identifiant
	private Reponse<DetailSalaire> getDetailSalaireById(Long id) {
		DetailSalaire salaire = null;

		try {
			salaire = salaireMetier.findById(id);
			if (salaire == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("DetailSalaire n'existe pas", id));
				new Reponse<DetailSalaire>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<DetailSalaire>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<DetailSalaire>(0, null, salaire);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un DetailSalaire  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/detailSalaire")
	public String creer(@RequestBody DetailSalaire detailSalaire) throws JsonProcessingException {
		Reponse<DetailSalaire> reponse;
		System.out.println(detailSalaire);
		try {

			DetailSalaire t1 = salaireMetier.creer(detailSalaire);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<DetailSalaire>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<DetailSalaire>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/detailSalaire")
	public String update(@RequestBody DetailSalaire  modif) throws JsonProcessingException {

		Reponse<DetailSalaire> reponse = null;
		Reponse<DetailSalaire> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getDetailSalaireById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				DetailSalaire detailSalaire = salaireMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", detailSalaire.getId()));
				reponse = new Reponse<DetailSalaire>(0, messages, detailSalaire);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<DetailSalaire>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("DetailSalaire n'existe pas"));
			reponse = new Reponse<DetailSalaire>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// obtenir un detailSalaire par son identifiant
	@GetMapping("/detailSalaire/{id}")
	public String getSalaireById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<DetailSalaire> reponse;
		try {
			DetailSalaire db = salaireMetier.findById(id);
			reponse = new Reponse<DetailSalaire>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer un DetailSalaire
	@DeleteMapping("/detailSalaire/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, salaireMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	// get all detailSalaire
		@GetMapping("/detailSalaire")
		public String findAll() throws JsonProcessingException {
			Reponse<List<DetailSalaire>> reponse;
			try {
				List<DetailSalaire> pers = salaireMetier.findAll();
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<DetailSalaire>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de DetailSalaire enregistrées");
					reponse = new Reponse<List<DetailSalaire>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}

}
