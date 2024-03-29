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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.Personne;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.JwtAuthenticationResponse;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IEmployeMetier;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.personne.IRoleMetier;
import ci.gestion.metier.security.JwtTokenProvider;
import ci.gestion.metier.utilitaire.Static;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class EmployeController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	IPersonneMetier personneMetier;

	@Autowired
	IRoleMetier roleMetier;
	@Autowired
	IEmployeMetier employeMetier;
	@Autowired
	JwtTokenProvider tokenProvider;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper employe par identifiant
		private Reponse<Employe> getEmployeById(Long id) {
			Employe employe = null;

			try {
				employe = employeMetier.findById(id);
				if (employe == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("employe n'existe pas", id));
					new Reponse<Employe>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Employe>(0, null, employe);
		}

	
	@PostMapping("/signinEmploye")
	public String authenticateUser(@Valid @RequestBody Personne loginRequest) throws JsonProcessingException {
		Reponse<ResponseEntity<?>> reponse;
		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		reponse = new Reponse<ResponseEntity<?>>(0, null, ResponseEntity.ok(new JwtAuthenticationResponse(jwt)));
		return jsonMapper.writeValueAsString(reponse);

	}

	@PostMapping("/signupEmpl")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createUser(@RequestBody Employe signUpRequest) throws Exception {
		System.out.println("Voir le type de la personne recuperée:" + signUpRequest.getType());

		Reponse<Employe> reponse = null;
		Employe employe = null;
		try {

			Role userRole = roleMetier.findByName(RoleName.ROLE_EMPLOYE).get();
			signUpRequest.setRoles(Collections.singleton(userRole));

			employe = employeMetier.creer(signUpRequest);

			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  a été créé avec succès", employe.getId()));
			reponse = new Reponse<Employe>(0, messages, employe);

		} catch (InvalideOryzException e) {
			reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/employe")
	public String update(@RequestBody Employe  modif) throws JsonProcessingException {
		System.out.println("modif recupere1:"+ modif);
		Reponse<Employe> reponse = null;
		Reponse<Employe> reponsePersModif = null;
		reponsePersModif = getEmployeById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				
				Employe empl = employeMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", empl.getId()));
				reponse = new Reponse<Employe>(0, messages, empl);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Employe n'existe pas"));
			reponse = new Reponse<Employe>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@PutMapping("/updateInfoEmploye")
	public String updateInformationEmploye(@RequestBody Employe  modif) throws JsonProcessingException {
		System.out.println("modif recupere1:"+ modif);
		Reponse<Employe> reponse = null;
		Employe p = employeMetier.findById(modif.getId());
		if (p != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				
				Employe empl = employeMetier.modifierInfoEmploye(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", empl.getId()));
				reponse = new Reponse<Employe>(0, messages, empl);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("l'employé ou l'entreprise n'existe pas"));
			reponse = new Reponse<Employe>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir un employe par son id
	@GetMapping("/employe/{id}")
	public String getById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Personne> reponse;
		try {
			Personne db = employeMetier.findById(id);
			reponse = new Reponse<Personne>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	
	// get all employe
	@GetMapping("/employe")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Employe>> reponse;
		try {
			List<Employe> pers = employeMetier.findAll();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Employe>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<Employe>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	@DeleteMapping("/employe/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, employeMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}
	@GetMapping("/listEmployeParEntreprise/{id}")
	public String getlistEmployeParEntreprise(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<Employe>> reponse;
		try {
			List<Employe> pers = employeMetier.listEmployeParEntreprise(id);
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Employe>>(0, null, pers);
				System.out.println("list empl par entreprise:"+ pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de departement enregistrés");
				reponse = new Reponse<List<Employe>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
	// Add role to employe
		@GetMapping("/addRoleToEmploye")
		public String rechercheAbonnesParParamettres(@RequestParam(value = "idEmploye") Long idEmploye,
				@RequestParam(value = "idRole") Long idRole) throws JsonProcessingException {
			Reponse<Employe> reponse;
			try {
				Employe em = employeMetier.addRoleToEmploye(idEmploye, idRole);
				reponse = new Reponse<Employe>(0, null, em);
			} catch (Exception e) {
				reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
////////rechercher un travail par mot cle
	@GetMapping("/rechercheEmployemc")
	public String chercherEmployeByMc(@RequestParam(value = "mc") String mc, @RequestParam(value = "nom") String nom) throws JsonProcessingException {
 
		Reponse<List<Employe>> reponse;
		try {
			List<Employe> travaux = employeMetier.chercherEmployeParMc(mc,nom);
  
			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<Employe>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrés");
				reponse = new Reponse<List<Employe>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Employe>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}
