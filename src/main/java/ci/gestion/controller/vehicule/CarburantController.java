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
import ci.gestion.entites.vehicule.Prestation;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;
import ci.gestion.metier.vehicule.CarburantMetier;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CarburantController {
	@Autowired
	private CarburantMetier carburantMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuper vehiculeMetier par identifiant
		private Reponse<Prestation> getCarburantById(Long id) {
			Prestation carburant = null;

			try {
				carburant = carburantMetier.findById(id);
				if (carburant == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("l'élément n'existe pas", id));
					new Reponse<Prestation>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Prestation>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Prestation>(0, null, carburant);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un Carburant dans  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/carburant")
		public String creer(@RequestBody Prestation carburant) throws JsonProcessingException {
			Reponse<Prestation> reponse;

			try {
				Prestation v = carburantMetier.creer(carburant);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", v.getId()));
				reponse = new Reponse<Prestation>(0, messages, v);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Prestation>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier une banque dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

		@PutMapping("/carburant")
		public String modifier(@RequestBody Prestation modif) throws JsonProcessingException {
			Reponse<Prestation> reponsePersModif = null;
			Reponse<Prestation> reponse = null;

			// on recupere le flash info a modifier
			reponsePersModif = getCarburantById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					Prestation t2 = carburantMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", t2.getId()));
					reponse = new Reponse<Prestation>(0, messages, t2);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<Prestation>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le vehicule n'existe pas"));
				reponse = new Reponse<Prestation>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer  Carburant  de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/carburant")
		public String findAllCarburant() throws JsonProcessingException, InvalideOryzException {
			Reponse<List<Prestation>> reponse;
			try {
				List<Prestation> flashs = carburantMetier.findAll();

				if (!flashs.isEmpty()) {
					reponse = new Reponse<List<Prestation>>(0, null, flashs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de vehicule info enregistrées");
					reponse = new Reponse<List<Prestation>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<List<Prestation>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	////////recuperer travail  par son id
		@GetMapping("/carburant/{id}")
		public String chercherBanqueById(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<Prestation> reponse = null;

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
				public String getcarburantDateParVehicule(
						@RequestParam(value = "vehiculeId") long vehiculeId,
						@RequestParam(value = "dateDebut") String dateDebut,
						@RequestParam(value = "dateFin") String dateFin) throws JsonProcessingException {
					Reponse<List<Prestation>> reponse;
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
					LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);
					try { 
						List<Prestation> carburants = carburantMetier.getCarburantVehiculeParDate(vehiculeId,dateTime,dateTime1); 
					  if (!carburants.isEmpty()) { 
						  reponse = new Reponse<List<Prestation>>(0, null, carburants); } else {
					  List<String> messages = new ArrayList<>();
					  messages.add("Pas d'achat de carburant enregistré"); reponse = new
					  Reponse<List<Prestation>>(1, messages, new ArrayList<>()); }
					  
					  } catch (Exception e) {
					  
					  reponse = new Reponse<>(1, Static.getErreursForException(e), null); }
					 
					return jsonMapper.writeValueAsString(null);
				}
				
				/*
				 * @GetMapping("/getCarburantByVehicule/{id}") public String
				 * getDepByEntreprise(@PathVariable Long id) throws JsonProcessingException {
				 * Reponse<List<Carburant>> reponse; try { List<Carburant> pers =
				 * carburantMetier.getCarburantVehicule(id); if (!pers.isEmpty()) { reponse =
				 * new Reponse<List<Carburant>>(0, null, pers); } else { List<String> messages =
				 * new ArrayList<>(); messages.add("Pas de véhicule(s) enregistré(s)"); reponse
				 * = new Reponse<List<Carburant>>(1, messages, new ArrayList<>()); }
				 * 
				 * } catch (Exception e) { reponse = new Reponse<>(1,
				 * Static.getErreursForException(e), null); } return
				 * jsonMapper.writeValueAsString(reponse);
				 * 
				 * }
				 */
				
				  @GetMapping("/getCarburantByEntreprise/{id}") public String
				  getCarburantByEntreprise(@PathVariable Long id) throws
				  JsonProcessingException { Reponse<List<Prestation>> reponse; try {
				  List<Prestation> pers = carburantMetier.getCarburantByEntreprise(id); if
				  (!pers.isEmpty()) { reponse = new Reponse<List<Prestation>>(0, null, pers); }
				  else { List<String> messages = new ArrayList<>();
				  messages.add("Pas de carburant(s) enregistré(s)"); reponse = new
				  Reponse<List<Prestation>>(1, messages, new ArrayList<>()); }
				  
				  } catch (Exception e) { reponse = new Reponse<>(1,
				  Static.getErreursForException(e), null); } return
				  jsonMapper.writeValueAsString(reponse);
				  
				  }
				 
}
