package ci.gestion.controller;

import java.util.ArrayList;
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

import ci.gestion.entites.operation.Employe;
import ci.gestion.metier.IEmployeMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class EmployeController {
	@Autowired
	private IEmployeMetier employeMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper employe par identifiant
	private Reponse<Employe> getAchatTravauxById(Long id) {
		Employe employe = null;

		try {
			employe = employeMetier.findById(id);
			if (employe == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Employe n'existe pas", id));
				new Reponse<Employe>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Employe>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Employe>(0, null, employe);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un employe  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/employe")
	public String creer(@RequestBody Employe employe) throws JsonProcessingException {
		Reponse<Employe> reponse;
		System.out.println(employe);
		try {

			Employe t1 = employeMetier.creer(employe);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Employe>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/employe")
	public String update(@RequestBody Employe  modif) throws JsonProcessingException {

		Reponse<Employe> reponse = null;
		Reponse<Employe> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getAchatTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Employe employe = employeMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", employe.getId()));
				reponse = new Reponse<Employe>(0, messages, employe);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Employe>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("AchatTravaux n'existe pas"));
			reponse = new Reponse<Employe>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// obtenir une location par son identifiant
	@GetMapping("/employe/{id}")
	public String getLocationById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<Employe> reponse;
		try {
			Employe db = employeMetier.findById(id);
			reponse = new Reponse<Employe>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer un achat
	@DeleteMapping("/employe/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, employeMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
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
						messages.add("Pas de Employe enregistrées");
						reponse = new Reponse<List<Employe>>(1, messages, new ArrayList<>());
					}
	
				} catch (Exception e) {
					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
	
			}
	
}
