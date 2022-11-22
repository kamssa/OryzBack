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

import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.projet.Projet;
import ci.gestion.metier.autreAchatTravaux.AutreAchatTravauxMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AutreAchatTravauxControlleur {
	@Autowired
	private AutreAchatTravauxMetier autreAchatTravauxMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper mainOeuvre par identifiant
	private Reponse<AutreAchatTravaux> getAutresTravauxById(Long id) {
		AutreAchatTravaux autres = null;

		try {
			autres = autreAchatTravauxMetier.findById(id);
			if (autres == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("autres n'existe pas", id));
				new Reponse<AutreAchatTravaux>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<AutreAchatTravaux>(0, null, autres);
	}
	////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer autres dans la base
	///////////////////////////////////////////////////////////////////////////

	@PostMapping("/autreAchatTravaux")
	public String creer(@RequestBody AutreAchatTravaux autres) throws JsonProcessingException {
		Reponse<AutreAchatTravaux> reponse;

		try {
			AutreAchatTravaux t1 = autreAchatTravauxMetier.creer(autres);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<AutreAchatTravaux>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	@PutMapping("/autreAchatTravaux")
	public String update(@RequestBody AutreAchatTravaux modif) throws JsonProcessingException {

		Reponse<AutreAchatTravaux> reponse = null;
		Reponse<AutreAchatTravaux> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:" + modif.getId());
		reponsePersModif = getAutresTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				AutreAchatTravaux autreAchatTravaux = autreAchatTravauxMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", autreAchatTravaux.getId()));
				reponse = new Reponse<AutreAchatTravaux>(0, messages, autreAchatTravaux);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<AutreAchatTravaux>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Autres n'existe pas"));
			reponse = new Reponse<AutreAchatTravaux>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

	// obtenir autres par son identifiant
	@GetMapping("/autreAchatTravaux/{id}")
	public String getAutreAchatsById(@PathVariable Long id) throws JsonProcessingException {
		Reponse<AutreAchatTravaux> reponse;
		try {
			AutreAchatTravaux db = autreAchatTravauxMetier.findById(id);
			reponse = new Reponse<AutreAchatTravaux>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	// supprimer autre
	@DeleteMapping("/autreAchatTravaux/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			reponse = new Reponse<Boolean>(0, null, autreAchatTravauxMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer achat par id travaux
	@GetMapping("/autreAchat/{idTravaux}")
	public String getAchatByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
		Reponse<List<AutreAchatTravaux>> reponse;

		try {
			List<AutreAchatTravaux> achats = autreAchatTravauxMetier.getAutreAchatTravauxTravauxByIdProjet(idTravaux);
			if (!achats.isEmpty()) {
				reponse = new Reponse<List<AutreAchatTravaux>>(0, null, achats);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'achat enregistré");
				reponse = new Reponse<List<AutreAchatTravaux>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer Detail Autre Achat par id travaux
	@GetMapping("/detailAutreAchatTravaux/{idTravaux}")
	public String getDetailAutreAchatTravauxByIdTravaux(@PathVariable("idTravaux") long idTravaux)
			throws JsonProcessingException {
		Reponse<List<DetailAutreAchatTravaux>> reponse;

		try {
			List<DetailAutreAchatTravaux> mainOeuvres = autreAchatTravauxMetier
					.findDetailAutreAchatTravauxByIdProjet(idTravaux);
			if (!mainOeuvres.isEmpty()) {
				reponse = new Reponse<List<DetailAutreAchatTravaux>>(0, null, mainOeuvres);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas Autres enregistré");
				reponse = new Reponse<List<DetailAutreAchatTravaux>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	// recuperer achat par id travaux
	@GetMapping("/montantAutreAchat/{idTravaux}")
	public String getAutreAchatMontantByIdTravaux(@PathVariable("idTravaux") long idTravaux)
			throws JsonProcessingException {
		Reponse<Double> reponse;

		try {
			Double achats = autreAchatTravauxMetier.findDetailAutreAchatTravauxMontantByIdProjet(idTravaux);
			reponse = new Reponse<Double>(0, null, achats);
			System.out.println("voir la somme" + achats);
		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////
	// recuperer achat par id travaux
	@GetMapping("/montantAutreAchatTravaux/{idTravaux}")
	public String getAchatMontantByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
		Reponse<Double> reponse;

		try {
			Double achats = autreAchatTravauxMetier.findAutreAchatTravauxMontantByIdProjet(idTravaux);
			reponse = new Reponse<Double>(0, null, achats);
			System.out.println("voir la somme" + achats);
		} catch (Exception e) {

			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

	/////////////////////////////////////////////////////////////
	@GetMapping("/detailAutreAchatTravauxDate")
	public String chercherTravauxByMc(@RequestParam(value = "travauxId") long travauxId,
			@RequestParam(value = "dateDebut") String dateDebut, @RequestParam(value = "dateFin") String dateFin)
			throws JsonProcessingException {
		System.out.println("Date bebut:" + dateDebut);
		Reponse<List<DetailAutreAchatTravaux>> reponse;
		// String str = "2022-05-31 00:00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
		LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);
		// LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, formatter);
		// LocalDateTime dateTime = LocalDateTime.parse(dateDebut);
		// LocalDateTime dateTime1 = LocalDateTime.parse(dateFin);
		System.out.println("Date convertie:" + dateTime);
		System.out.println(" travauxId:" + travauxId);
		// System.out.println("Date convertie1:"+ dateTime1);
		// Next parse the date from the @RequestParam, specifying the TO type as

		try {
			List<DetailAutreAchatTravaux> travaux = autreAchatTravauxMetier.getDetailAutreAchatTravauxBydate(travauxId,
					dateTime, dateTime1);

			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<DetailAutreAchatTravaux>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'enregistrement !");
				reponse = new Reponse<List<DetailAutreAchatTravaux>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<DetailAutreAchatTravaux>>(1, Static.getErreursForException(e),
					new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

/////////////////////////////////////////////////////////////
	@GetMapping("/autreAchatTravauxDate")
	public String achatTravauxByDate(@RequestParam(value = "travauxId") long travauxId,
			@RequestParam(value = "dateDebut") String dateDebut, @RequestParam(value = "dateFin") String dateFin)
			throws JsonProcessingException {
		System.out.println("Date bebut:" + dateDebut);
		Reponse<List<AutreAchatTravaux>> reponse;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
		LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);

		System.out.println("Date convertie:" + dateTime);
		System.out.println(" travauxId:" + travauxId);

		try {
			List<AutreAchatTravaux> travaux = autreAchatTravauxMetier.getAutreAchatTravauxBydate(travauxId,
					dateTime, dateTime1);

			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<AutreAchatTravaux>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas d'enregistrement !");
				reponse = new Reponse<List<AutreAchatTravaux>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<AutreAchatTravaux>>(1, Static.getErreursForException(e),
					new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	//////// rechercher un travail par mot cle
	@GetMapping("/rechercheAutreAchatTravauxParNum")
	public String chercherTravauxByMc(@RequestParam(value = "numeroFacture") String numeroFacture,
			@RequestParam(value = "projetId") long projetId) throws JsonProcessingException {

		Reponse<AutreAchatTravaux> reponse;
		try {
			AutreAchatTravaux db = autreAchatTravauxMetier.chercherAutreAchatTravauxParMc(numeroFacture, projetId);
			reponse = new Reponse<AutreAchatTravaux>(0, null, db);
		} catch (Exception e) {
			reponse = new Reponse<>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);

	}

}
