package ci.gestion.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.personne.Personne;
import ci.gestion.entites.personne.Role;
import ci.gestion.entites.personne.RoleName;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.JwtAuthenticationResponse;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.personne.IRoleMetier;
import ci.gestion.metier.security.JwtTokenProvider;
import ci.gestion.metier.utilitaire.Static;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthController {
	
    AuthenticationManager authenticationManager;
    IPersonneMetier userMetier;
    IRoleMetier roleMetier;
    JwtTokenProvider tokenProvider;
	private ObjectMapper jsonMapper;

    @PostMapping("/signin")
    public String authenticateUser(@Valid @RequestBody Personne loginRequest) throws JsonProcessingException {
    	Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
					return jsonMapper.writeValueAsString(reponse);

	}

	
	  @PostMapping("/signup")
	  @ResponseStatus(code = HttpStatus.CREATED) public String
	  creaeUser(@RequestBody Personne signUpRequest) throws Exception {
	  
	  Reponse<Personne> reponse = null; Personne user = null; try {
	  
	  Role userRole = roleMetier.findByName(RoleName.ROLE_ADMIN).get();
	  signUpRequest.setRoles(Collections.singleton(userRole));
	  
	  user = userMetier.creer(signUpRequest);
	  
	  List<String> messages = new ArrayList<>();
	  messages.add(String.format("%s  a été créé avec succès", user.getId()));
	  reponse = new Reponse<Personne>(0, messages, user);
	  
	  } catch (InvalideOryzException e) { reponse = new Reponse<Personne>(1,
	  Static.getErreursForException(e), null); } return
	  jsonMapper.writeValueAsString(reponse); }
	 
}