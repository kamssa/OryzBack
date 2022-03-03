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
import ci.gestion.metier.caisse.CaisseMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CaisseControlleur {
	@Autowired
	private CaisseMetier caisseMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper mainOeuvre par identifiant
	private Reponse<Caisse> getCaisseById(Long id) {
		Caisse autres = null;

		try {
			autres = caisseMetier.findById(id);
			if (autres == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Caisse n'existe pas", id));
				new Reponse<Caisse>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Caisse>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Caisse>(0, null, autres);
	}
////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer  autres  dans la base
///////////////////////////////////////////////////////////////////////////

	@PostMapping("/caisse")
	public String creer(@RequestBody Caisse autres) throws JsonProcessingException {
		Reponse<Caisse> reponse;
		try {
			Caisse t1 = caisseMetier.creer(autres);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Caisse>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Caisse>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// obtenir autres par son identifiant
				@GetMapping("/caisse/{id}")
				public String getCaisseByIdCaisse(@PathVariable Long id) throws JsonProcessingException {
					Reponse<Caisse> reponse;
					try {
						Caisse db = caisseMetier.findById(id);
						reponse = new Reponse<Caisse>(0, null, db);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
}
