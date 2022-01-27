package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

}
