package ci.gestion.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.Personne;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.metier.entreprise.EntrepriseMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.personne.IPersonneMetier;
import ci.gestion.metier.personne.IRoleMetier;
import ci.gestion.metier.utilitaire.Static;





@RestController
@RequestMapping("/api")
@CrossOrigin
public class EntrepriseController {
	@Autowired
	private EntrepriseMetier entrepriseMetier;
	@Autowired
	IRoleMetier roleMetier;
	@Autowired
	IPersonneMetier personneMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper employe par identifiant
	private Reponse<Entreprise> getEntrepriseByIdEntreprise(Long id) {
		Entreprise entreprise = null;

		try {
			entreprise = entrepriseMetier.findById(id);
			if (entreprise == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("entreprise n'existe pas", id));
				new Reponse<Entreprise>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Entreprise>(0, null, entreprise);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une entreprise  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/entreprise")
	public String creer(@RequestBody Entreprise entreprise) throws JsonProcessingException {


		Reponse<Entreprise> reponse = null;
		Entreprise entre = null;
		try {

			Role userRole = roleMetier.findByName(RoleName.ROLE_ENTREPRISE).get();
			entreprise.setRoles(Collections.singleton(userRole));
			entre = entrepriseMetier.creer(entreprise);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s a été créé avec succès", entre.getNom()));
			reponse = new Reponse<Entreprise>(0, messages, entre);

		} catch (InvalideOryzException e) {
			reponse = new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/entreprise")
	public String update(@RequestBody Entreprise  modif) throws JsonProcessingException {

		Reponse<Entreprise> reponse = null;
		Reponse<Entreprise> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getEntrepriseByIdEntreprise(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Role userRole = roleMetier.findByName(RoleName.ROLE_ENTREPRISE).get();
				modif.setRoles(Collections.singleton(userRole));
				Entreprise employe = entrepriseMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", employe.getId()));
				reponse = new Reponse<Entreprise>(0, messages, employe);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Entreprise>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Entreprise n'existe pas"));
			reponse = new Reponse<Entreprise>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	
	// obtenir une location par son identifiant
	@GetMapping("/entreprise/{id}")
	public String getEntrepriseById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Entreprise> reponse;
		try {
			Entreprise db = entrepriseMetier.findById(id);
			reponse = new Reponse<Entreprise>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer un achat
	@DeleteMapping("/entreprise/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, entrepriseMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	// get all Entreprise
			@GetMapping("/entreprise")
			public String findAll() throws JsonProcessingException {
				Reponse<List<Entreprise>> reponse;
				try {
					List<Entreprise> pers = entrepriseMetier.findAll();
					if (!pers.isEmpty()) {
						reponse = new Reponse<List<Entreprise>>(0, null, pers);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas de Employe enregistrées");
						reponse = new Reponse<List<Entreprise>>(1, messages, new ArrayList<>());
					}
	
				} catch (Exception e) {
					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
	
			}
			
			
}
