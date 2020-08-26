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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.Role;
import ci.gestion.entites.RoleName;
import ci.gestion.entites.User;
import ci.gestion.metier.IRoleMetier;
import ci.gestion.metier.IUserMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.JwtAuthenticationResponse;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.security.JwtTokenProvider;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserMetier userMetier;

    @Autowired
    IRoleMetier roleMetier;

   @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
	private ObjectMapper jsonMapper;

    @PostMapping("/signin")
    public String authenticateUser(@Valid @RequestBody User loginRequest) throws JsonProcessingException {
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
public String creaeUser(@RequestBody User signUpRequest) throws Exception {

	Reponse<User> reponse = null;
	User user = null;
	try {

		Role userRole = roleMetier.findByName(RoleName.ROLE_ADMIN).get();
		signUpRequest.setRoles(Collections.singleton(userRole));
	
		user = userMetier.creer(signUpRequest);

		List<String> messages = new ArrayList<>();
		messages.add(String.format("%s  a été créé avec succès", user.getId()));
		reponse = new Reponse<User>(0, messages, user);

	} catch (InvalideOryzException e) {
		reponse = new Reponse<User>(1, Static.getErreursForException(e), null);
	}
	return jsonMapper.writeValueAsString(reponse);
}
}