package ci.gestion.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

import ci.gestion.entites.shared.Personne;
import ci.gestion.entites.site.Client;
import ci.gestion.metier.ClientMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.utilitaire.Static;



@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClientControlleur {
	
	@Autowired
	IPersonneMetier personneMetier;
	@Autowired
	private ClientMetier clientMetier;

	@Autowired
	private ObjectMapper jsonMapper;
	@Autowired
	ApplicationEventPublisher eventPublisher;

	
	@PostMapping("/client")
	public String creer(@RequestBody Client client) throws JsonProcessingException {
		Reponse<Client> reponse;
		System.out.println(client);
		try {
			
			Client d = clientMetier.creer(client);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Client>(0, messages, d);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Client>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/client")
	public String update(@RequestBody Client modif) throws JsonProcessingException {

		Reponse<Client> reponse = null;
		Reponse<Client> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:" + modif);
		Client client = clientMetier.findById(modif.getId());
		if (client != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				Client c = clientMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", c.getId()));
				reponse = new Reponse<Client>(0, messages, c);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Client>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Client n'existe pas"));
			reponse = new Reponse<Client>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// get all demande
	@GetMapping("/client")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Client>> reponse;
		try {
			List<Client> clients = clientMetier.findAll();
			if (!clients.isEmpty()) {
				reponse = new Reponse<List<Client>>(0, null, clients);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de client enregistrés");
				reponse = new Reponse<List<Client>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer une demande
	@DeleteMapping("/client/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, clientMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	// recherche les employes par id
	@GetMapping("/client/{id}")
	public String getPersonnesById(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Personne> reponse;

		try {

			Personne p = personneMetier.findById(id);
			List<String> messages = new ArrayList<>();
			messages.add(String.format(" à été créer avec succes"));
			reponse = new Reponse<Personne>(0, messages, p);

		} catch (Exception e) {

			reponse = new Reponse<Personne>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	

}
