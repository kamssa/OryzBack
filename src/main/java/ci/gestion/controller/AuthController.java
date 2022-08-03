 package ci.gestion.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ci.gestion.entites.shared.Personne;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.JwtAuthenticationResponse;
import ci.gestion.metier.model.LoginRequest;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.personne.IRoleMetier;
import ci.gestion.metier.security.JwtTokenProvider;
import ci.gestion.metier.utilitaire.Static;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IPersonneMetier personneMetier;

	@Autowired
	IRoleMetier roleMetier;

	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

	@PostMapping("/signin")
	public String authenticateUser(@Valid @RequestBody Personne  loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/signup")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String creaeUser(@RequestBody Personne signUpRequest) throws Exception {
		

		Reponse<Personne> reponse = null;
		Personne personne = null;
		try {

			Role userRole = roleMetier.findByName(RoleName.ROLE_ADMIN).get();
			signUpRequest.setRoles(Collections.singleton(userRole));
             personne = personneMetier.creer(signUpRequest);
			System.out.println("Voir le type de la personne recuperée:" + personne.getType());
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  a été créé avec succès", personne.getId()));
			reponse = new Reponse<Personne>(0, messages, personne);

		} catch (InvalideOryzException e) {
			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// obtenir un manager par son email
				@GetMapping("/personneByEmail/{email}")
				public String getByEmail(@PathVariable String email) throws JsonProcessingException {
					Reponse<Personne> reponse;
					try {
						Personne personne = personneMetier.findByEmail(email).get();
						reponse = new Reponse<Personne>(0, null, personne);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
				// obtenir une personne par son id
				@GetMapping("/getPersonneById/{id}")
				public String getPersonneById(@PathVariable Long id) throws JsonProcessingException {
					Reponse<Personne> reponse;
					try {
						Personne personne = personneMetier.findById(id);
						reponse = new Reponse<Personne>(0, null, personne);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
}