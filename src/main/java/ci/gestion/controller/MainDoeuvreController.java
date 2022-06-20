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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.mainOeuvre.IMainDoeuvreMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class MainDoeuvreController {
	@Autowired
	private IMainDoeuvreMetier mainDoeuvreMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper mainOeuvre par identifiant
	private Reponse<MainOeuvre> getLoyerTravauxById(Long id) {
		MainOeuvre mainOeuvre = null;

		try {
			mainOeuvre = mainDoeuvreMetier.findById(id);
			if (mainOeuvre == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Loyer n'existe pas", id));
				new Reponse<MainOeuvre>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<MainOeuvre>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<MainOeuvre>(0, null, mainOeuvre);
	}
////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un mainOeuvre  dans la base
///////////////////////////////////////////////////////////////////////////

	@PostMapping("/mainOeuvre")
	public String creer(@RequestBody MainOeuvre mainOeuvre) throws JsonProcessingException {
		Reponse<MainOeuvre> reponse;
        System.out.println(mainOeuvre);
		try {
            MainOeuvre t1 = mainDoeuvreMetier.creer(mainOeuvre);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<MainOeuvre>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<MainOeuvre>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/mainOeuvre")
	public String update(@RequestBody MainOeuvre  modif) throws JsonProcessingException {

		Reponse<MainOeuvre> reponse = null;
		Reponse<MainOeuvre> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getLoyerTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				MainOeuvre achatTravaux = mainDoeuvreMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", achatTravaux.getId()));
				reponse = new Reponse<MainOeuvre>(0, messages, achatTravaux);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<MainOeuvre>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Autres n'existe pas"));
			reponse = new Reponse<MainOeuvre>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer MainOeuvre par id travaux
		@GetMapping("/mainOeuvre/{idTravaux}")
		public String getMainOeuvreByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
			Reponse<List<MainOeuvre>> reponse;

			try {
				List<MainOeuvre> mainOeuvres = mainDoeuvreMetier.findMainOeuvreByIdTravaux(idTravaux);
				if (!mainOeuvres.isEmpty()) {
					reponse = new Reponse<List<MainOeuvre>>(0, null, mainOeuvres);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas Autres enregistré");
					reponse = new Reponse<List<MainOeuvre>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {

				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir autres par son identifiant
		@GetMapping("/mainOeuvres/{id}")
		public String getAutresById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<MainOeuvre> reponse;
			try {
				MainOeuvre db = mainDoeuvreMetier.findById(id);
				reponse = new Reponse<MainOeuvre>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer autre
		@DeleteMapping("/mainOeuvre/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, mainDoeuvreMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
		@DeleteMapping("/DeleteDetailMain/{idMain}/{idDetail}")
		public String suppDetail(@PathVariable Long idMain, @PathVariable Long idDetail) throws JsonProcessingException {
			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, mainDoeuvreMetier.supprimerDetailMainOeuvre(idMain, idDetail));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		
		}
		// recuperer Detail MainOeuvre par id travaux
				@GetMapping("/detailMainOeuvre/{idTravaux}")
				public String getDetailMainOeuvreByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
					Reponse<List<DetailMainOeuvre>> reponse;

					try {
						List<DetailMainOeuvre> mainOeuvres = mainDoeuvreMetier.findDetailMainOeuvreByIdTravaux(idTravaux);
						if (!mainOeuvres.isEmpty()) {
							reponse = new Reponse<List<DetailMainOeuvre>>(0, null, mainOeuvres);
						} else {
							List<String> messages = new ArrayList<>();
							messages.add("Pas Autres enregistré");
							reponse = new Reponse<List<DetailMainOeuvre>>(1, messages, new ArrayList<>());
						}

					} catch (Exception e) {

						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);
				}
				// recuperer achat par id travaux
				@GetMapping("/montantMainDoeuvre/{idTravaux}")
				public String getmontantMainDoeuvreByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
					Reponse<Double> reponse;
                    
					try {
						Double achats = mainDoeuvreMetier.findDetailMainOeuvreMontantByIdTravaux(idTravaux);
						reponse = new Reponse<Double>(0, null, achats);
						System.out.println("voir la somme"+ achats);
					} catch (Exception e) {

						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);
				}
				@GetMapping("/detailMainDate")
				public String chercherTravauxByMc(
						@RequestParam(value = "travauxId") long travauxId,
						@RequestParam(value = "dateDebut") String dateDebut, 
				        @RequestParam(value = "dateFin") String dateFin) throws JsonProcessingException {
					System.out.println("Date bebut:"+ dateDebut);
					Reponse<List<DetailMainOeuvre>> reponse;
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
						List<DetailMainOeuvre> travaux = mainDoeuvreMetier.getDetailMainBydate(travauxId,dateTime,dateTime1);
			  
						if (!travaux.isEmpty()) {
							reponse = new Reponse<List<DetailMainOeuvre>>(0, null, travaux);
						} else {
							List<String> messages = new ArrayList<>();
							messages.add("Pas d'enregistrement !");
							reponse = new Reponse<List<DetailMainOeuvre>>(2, messages, new ArrayList<>());
						}

					} catch (Exception e) {
						reponse = new Reponse<List<DetailMainOeuvre>>(1, Static.getErreursForException(e), new ArrayList<>());
					}
					return jsonMapper.writeValueAsString(reponse);

				}
}
