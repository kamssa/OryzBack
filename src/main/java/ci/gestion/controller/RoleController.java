package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.Personne;
import ci.gestion.entites.shared.Role;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IRoleMetier;
import ci.gestion.metier.utilitaire.Static;
import lombok.AllArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class RoleController {
	 IRoleMetier roleMetier;
	 private ObjectMapper jsonMapper;

		@PostMapping("/role")
		public String creer(@RequestBody Role role) throws JsonProcessingException {
			Reponse<Role> reponse;

			try {

				Role t1 = roleMetier.creer(role);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", t1.getName()));
				reponse = new Reponse<Role>(0, messages, t1);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Role>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir un employe par son id
		@GetMapping("/role/{id}")
		public String getRoleById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Role> reponse;
			try {
				Role db = roleMetier.findById(id);
				reponse = new Reponse<Role>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// get all employe
		@GetMapping("/role")
		public String findAll() throws JsonProcessingException {
			Reponse<List<Role>> reponse;
			try {
				List<Role> pers = roleMetier.findAll();
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Role>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de departement enregistrés");
					reponse = new Reponse<List<Role>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
}
