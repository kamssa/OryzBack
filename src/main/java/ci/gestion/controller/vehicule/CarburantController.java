package ci.gestion.controller.vehicule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;
import ci.gestion.metier.vehicule.CarburantMetier;
import ci.gestion.metier.vehicule.VehiculeMetier;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CarburantController {
	@Autowired
	private CarburantMetier carburantMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuper vehiculeMetier par identifiant
		private Reponse<Carburant> getCarburantById(Long id) {
			Carburant carburant = null;

			try {
				carburant = carburantMetier.findById(id);
				if (carburant == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("l'élément n'existe pas", id));
					new Reponse<Carburant>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Carburant>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Carburant>(0, null, carburant);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un Carburant dans  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/carburant")
		public String creer(@RequestBody Carburant carburant) throws JsonProcessingException {
			Reponse<Carburant> reponse;

			try {
				Carburant v = carburantMetier.creer(carburant);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", v.getId()));
				reponse = new Reponse<Carburant>(0, messages, v);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Carburant>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier une banque dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

		@PutMapping("/carburant")
		public String modifier(@RequestBody Carburant modif) throws JsonProcessingException {
			Reponse<Carburant> reponsePersModif = null;
			Reponse<Carburant> reponse = null;

			// on recupere le flash info a modifier
			reponsePersModif = getCarburantById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					Carburant t2 = carburantMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", t2.getId()));
					reponse = new Reponse<Carburant>(0, messages, t2);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<Carburant>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le vehicule n'existe pas"));
				reponse = new Reponse<Carburant>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer  Carburant  de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/carburant")
		public String findAllCarburant() throws JsonProcessingException, InvalideOryzException {
			Reponse<List<Carburant>> reponse;
			try {
				List<Carburant> flashs = carburantMetier.findAll();

				if (!flashs.isEmpty()) {
					reponse = new Reponse<List<Carburant>>(0, null, flashs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de vehicule info enregistrées");
					reponse = new Reponse<List<Carburant>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<List<Carburant>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	////////recuperer travail  par son id
		@GetMapping("/carburant/{id}")
		public String chercherBanqueById(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<Carburant> reponse = null;

			reponse = getCarburantById(id);
			if (reponse.getBody() == null) {
				throw new RuntimeException("pas d'enregistrement pour ce Carburant");
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer un achat
				@DeleteMapping("/carburant/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						reponse = new Reponse<Boolean>(0, null, carburantMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
		 
					return jsonMapper.writeValueAsString(reponse);
				}
				// recuperer Detail MainOeuvre par id travaux
				@GetMapping("/carburantDateParVehicule")
				public String getCarburantDateParVehicule(
						@RequestParam(value = "vehicule") Vehicule vehicule,
						@RequestParam(value = "dateDebut") String dateDebut,
						@RequestParam(value = "dateFin") String dateFin) throws JsonProcessingException {
					Reponse<List<Carburant>> reponse;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
					LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);
					try { List<Carburant> mainOeuvres = carburantMetier.findCarburantByDateBetweenAndEntreprise(vehicule,dateTime,dateTime1); 
					  if (!mainOeuvres.isEmpty()) { 
						  reponse = new Reponse<List<Carburant>>(0, null, mainOeuvres); } else {
					  List<String> messages = new ArrayList<>();
					  messages.add("Pas Autres enregistré"); reponse = new
					  Reponse<List<Carburant>>(1, messages, new ArrayList<>()); }
					  
					  } catch (Exception e) {
					  
					  reponse = new Reponse<>(1, Static.getErreursForException(e), null); }
					 
					return jsonMapper.writeValueAsString(null);
				}
}
