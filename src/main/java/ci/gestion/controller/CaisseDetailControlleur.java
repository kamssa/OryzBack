package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.caisse.Caisse;
import ci.gestion.entites.caisse.CaisseDetail;
import ci.gestion.entites.stock.Categorie;
import ci.gestion.metier.caisse.CaisseDetailMetier;
import ci.gestion.metier.caisse.CaisseMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CaisseDetailControlleur {
	@Autowired
	private CaisseDetailMetier caisseMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper mainOeuvre par identifiant
	private Reponse<CaisseDetail> getCaisseDetailDetailById(Long id) {
		CaisseDetail autres = null;

		try {
			autres = caisseMetier.findById(id);
			if (autres == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Caisse n'existe pas", id));
				new Reponse<CaisseDetail>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<CaisseDetail>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<CaisseDetail>(0, null, autres);
	}
////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer  autres  dans la base
///////////////////////////////////////////////////////////////////////////

	@PostMapping("/caisseDetail")
	public String creer(@RequestBody CaisseDetail autres) throws JsonProcessingException {
		Reponse<CaisseDetail> reponse;
		try {
			CaisseDetail t1 = caisseMetier.creer(autres);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<CaisseDetail>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<CaisseDetail>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/getCaisseDetailByIdEntreprise/{id}")
	public String getCaisseDetailByEntreprise(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<CaisseDetail>> reponse;
		try {
			List<CaisseDetail> pers = caisseMetier.findCaisseDetailByIdEntreprise(id);
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<CaisseDetail>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<CaisseDetail>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
}
