package ci.gestion.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.banque.Operation;
import ci.gestion.metier.banque.IOperationBanqueMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OperationBanqueController {
	@Autowired
	private IOperationBanqueMetier operationBanqueMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;
	// recuper travaux par identifiant
		private Reponse<Operation> getOperationBanqueById(Long id) {
			Operation operation = null;

			try {
				operation = operationBanqueMetier.findById(id);
				if (operation == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("operation n'existe pas", id));
					new Reponse<Operation>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Operation>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Operation>(0, null, operation);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un Achat  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/operation")
		public String creer(@RequestBody Operation op) throws JsonProcessingException {
			Reponse<Operation> reponse;
	        System.out.println("Voir la date operation:");
			try {

				Operation t1 = operationBanqueMetier.creer(op);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes"));
				reponse = new Reponse<Operation>(0, messages, t1);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Operation>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		@PutMapping("/operation")
		public String update(@RequestBody Operation  modif) throws JsonProcessingException {

			Reponse<Operation> reponse = null;
			Reponse<Operation> reponsePersModif = null;
			// on recupere operation a modifier
			System.out.println("modif recupere1:"+ modif);
			reponsePersModif = getOperationBanqueById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					System.out.println("modif recupere2:"+ modif);
					Operation operation = operationBanqueMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", operation.getId()));
					reponse = new Reponse<Operation>(0, messages, operation);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<Operation>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Operation n'existe pas"));
				reponse = new Reponse<Operation>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir tous les abonnements par leur identifiant
		@GetMapping("/operation/{id}")
		public String getById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Operation> reponse;
			try {
				Operation operation = operationBanqueMetier.findById(id);
				reponse = new Reponse<Operation>(0, null, operation);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	// get all abonnements
	@GetMapping("/operation")
	public String findAll() throws JsonProcessingException {
		Reponse<List<Operation>> reponse;
		try {
			List<Operation> pers = operationBanqueMetier.findAll();
			if (!pers.isEmpty()) {
				reponse = new Reponse<List<Operation>>(0, null, pers);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'abonnés enregistrées");
				reponse = new Reponse<List<Operation>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}
			// supprimer un achat
			@DeleteMapping("/operation/{id}")
			public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;

				try {

					reponse = new Reponse<Boolean>(0, null, operationBanqueMetier.supprimer(id));

				} catch (RuntimeException e1) {
					reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
				}

				return jsonMapper.writeValueAsString(reponse);
			}
			// recherche avec plusieurs parametre
			@GetMapping("/getOperationByParam/{dateDebut}/{dateFin}/{nom}")
			public String rechercherOperationParParam(
					@PathVariable("dateDebut") Date dateDebut,
					@PathVariable("dateFin") Date dateFin,
					@PathVariable("libelle")String libelle,
					@PathVariable("nom")   String nom) throws JsonProcessingException {
				Reponse<List<Operation>> reponse;
				try {
					List<Operation> db = operationBanqueMetier.findOperationByParam(dateDebut, dateFin, nom);
					reponse = new Reponse<List<Operation>>(0, null, db);
				} catch (Exception e) {
					reponse = new Reponse<List<Operation>>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);

			}

}
