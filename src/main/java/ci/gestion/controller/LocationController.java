package ci.gestion.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.location.ILocationMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LocationController {
	@Autowired
	private ILocationMetier locationMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper location par identifiant
	private Reponse<LocationTravaux> getAchatTravauxById(Long id) {
		LocationTravaux location = null;

		try {
			location = locationMetier.findById(id);
			if (location == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Location n'existe pas", id));
				new Reponse<LocationTravaux>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<LocationTravaux>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<LocationTravaux>(0, null, location);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une Location  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/location")
	public String creer(@RequestBody LocationTravaux location) throws JsonProcessingException {
		Reponse<LocationTravaux> reponse;
        System.out.println(location);
		try {

			LocationTravaux t1 = locationMetier.creer(location);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<LocationTravaux>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<LocationTravaux>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	// recuperer location par id travaux
			@GetMapping("/location/{idTravaux}")
			public String getLocationByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
				Reponse<List<LocationTravaux>> reponse;

				try {
					List<LocationTravaux> locations = locationMetier.findLocationByIdProjet(idTravaux);
					if (!locations.isEmpty()) {
						reponse = new Reponse<List<LocationTravaux>>(0, null, locations);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas de location enregistré");
						reponse = new Reponse<List<LocationTravaux>>(1, messages, new ArrayList<>());
					}

				} catch (Exception e) {

					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
			}
			// obtenir une location par son identifiant
			@GetMapping("/locations/{id}")
			public String getLocationById(@PathVariable Long id) throws JsonProcessingException {
				Reponse<LocationTravaux> reponse;
				try {
					LocationTravaux db = locationMetier.findById(id);
					reponse = new Reponse<LocationTravaux>(0, null, db);
				} catch (Exception e) {
					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);

			}
			// supprimer un achat
			@DeleteMapping("/location/{id}")
			public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

				Reponse<Boolean> reponse = null;

				try {

					reponse = new Reponse<Boolean>(0, null, locationMetier.supprimer(id));

				} catch (RuntimeException e1) {
					reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
				}

				return jsonMapper.writeValueAsString(reponse);
			}
			@DeleteMapping("/DeleteDetailLocation/{idLocation}/{idDetail}")
			public String suppDetail(@PathVariable Long idLocation, @PathVariable Long idDetail) throws JsonProcessingException {
				Reponse<Boolean> reponse = null;

				try {

					reponse = new Reponse<Boolean>(0, null, locationMetier.supprimerDetailLocation(idLocation, idDetail));

				} catch (RuntimeException e1) {
					reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
				}

				return jsonMapper.writeValueAsString(reponse);
			
			}
			// recuperer Detail location  par id travaux
			@GetMapping("/detailLocation/{idTravaux}")
			public String getDetailLocationByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
				Reponse<List<DetailLocation>> reponse;

				try {
					List<DetailLocation> mainOeuvres = locationMetier.findDetailLocationByIdProjet(idTravaux);
					if (!mainOeuvres.isEmpty()) {
						reponse = new Reponse<List<DetailLocation>>(0, null, mainOeuvres);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas Autres enregistré");
						reponse = new Reponse<List<DetailLocation>>(1, messages, new ArrayList<>());
					}

				} catch (Exception e) {

					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
			}
			// recuperer achat par id travaux
			@GetMapping("/montantLocation/{idTravaux}")
			public String getMontantLocationByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
				Reponse<Double> reponse;
                
				try {
					Double achats = locationMetier.findDetailLocationMontantByIdProjet(idTravaux);
					reponse = new Reponse<Double>(0, null, achats);
					System.out.println("voir la somme"+ achats);
				} catch (Exception e) {

					reponse = new Reponse<>(1, Static.getErreursForException(e), null);
				}
				return jsonMapper.writeValueAsString(reponse);
			}
			@GetMapping("/detailLocationDate")
			public String chercherTravauxByMc(
					@RequestParam(value = "travauxId") long travauxId,
					@RequestParam(value = "dateDebut") String dateDebut, 
			        @RequestParam(value = "dateFin") String dateFin) throws JsonProcessingException {
				System.out.println("Date bebut:"+ dateDebut);
				Reponse<List<DetailLocation>> reponse;
				//String str = "2022-05-31 00:00:00"; 
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
				LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);
				//LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, formatter);
				//LocalDateTime dateTime = LocalDateTime.parse(dateDebut);
				//LocalDateTime dateTime1 = LocalDateTime.parse(dateFin);
			   System.out.println("Date convertie:"+ dateTime);
			  // System.out.println("Date convertie1:"+ dateTime1);
				    //Next parse the date from the @RequestParam, specifying the TO type as 
				   
				try {
					List<DetailLocation> travaux = locationMetier.getDetailLocationBydate(travauxId,dateTime,dateTime1);
		  
					if (!travaux.isEmpty()) {
						reponse = new Reponse<List<DetailLocation>>(0, null, travaux);
					} else {
						List<String> messages = new ArrayList<>();
						messages.add("Pas d'enregistrement !");
						reponse = new Reponse<List<DetailLocation>>(2, messages, new ArrayList<>());
					}

				} catch (Exception e) {
					reponse = new Reponse<List<DetailLocation>>(1, Static.getErreursForException(e), new ArrayList<>());
				}
				return jsonMapper.writeValueAsString(reponse);

			}
			
}
