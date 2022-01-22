package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.personne.Personne;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.utilitaire.Static;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdminController {

	@Autowired
	IPersonneMetier personneMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	// recherche les personne par id
		@GetMapping("/admin/{id}")
		public String getAdminById(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Personne> reponse;

			try {

				Personne p = personneMetier.findById(id);
				List<String> messages = new ArrayList<>();
				messages.add(String.format(" à été recupere avec succes"));
				reponse = new Reponse<Personne>(0, messages, p);

			} catch (Exception e) {

				reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

}
