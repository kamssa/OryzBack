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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.projet.Photo;
import ci.gestion.entites.projet.Projet;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.image.IPhotoMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.projet.ProjetMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProjetController {
	@Autowired
	private ProjetMetier projetMetier;
	@Autowired
	private IPhotoMetier photoMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	

// recuper travaux par identifiant
	private Reponse<Projet> getProjetById(Long id) {
		Projet travaux = null;

		try {
			travaux = projetMetier.findById(id);
			if (travaux == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("le travail n'existe pas", id));
				new Reponse<Projet>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Projet>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Projet>(0, null, travaux);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un travail dans  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/projet")
	public String creer(@RequestBody Projet travaux) throws JsonProcessingException {
		Reponse<Projet> reponse;

		try {

			Projet t1 = projetMetier.creer(travaux);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Projet>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Projet>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

/////////////////////////////////////////////////////////////////////////////////////////
// modifier un travail dans la base de donnee
///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/projet")
	public String modifier(@RequestBody Projet modif) throws JsonProcessingException {
		Reponse<Projet> reponsePersModif = null;
		Reponse<Projet> reponse = null;

		// on recupere le flash info a modifier
		reponsePersModif = getProjetById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Projet t2 = projetMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", t2.getId()));
				reponse = new Reponse<Projet>(0, messages, t2);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Projet>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le travail n'existe pas"));
			reponse = new Reponse<Projet>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

///////////////////////////////////////////////////////////////////////////////////////////////
// recuperer tous les Travaux  de la base de
/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/projet")
	public String findAllTrauxInfo() throws JsonProcessingException, InvalideOryzException {
		Reponse<List<Projet>> reponse;
		try {
			List<Projet> flashs = projetMetier.findAll();

			if (!flashs.isEmpty()) {
				reponse = new Reponse<List<Projet>>(0, null, flashs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrées");
				reponse = new Reponse<List<Projet>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Projet>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
	@GetMapping("/projet/{id}")
	public String chercherTravauxById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Projet> reponse = null;

		reponse = getProjetById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour ce travail");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
	@GetMapping("/projetByIdEntreprise/{id}")
	public String travauxByIdSite(@PathVariable long id) throws JsonProcessingException {
		Reponse<List<Projet>> reponse;
		try {
			List<Projet> travaux = projetMetier.findProjetBIdEntreprise(id);

			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<Projet>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrés");
				reponse = new Reponse<List<Projet>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Projet>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////rechercher un travail par mot cle
	@GetMapping("/rechercheProjetmc")
	public String chercherTravauxByMc(@RequestParam(value = "mc") String mc, @RequestParam(value = "nom") String nom) throws JsonProcessingException {
 System.out.println(mc);
 System.out.println(nom);

		Reponse<List<Projet>> reponse;
		try {
			List<Projet> travaux = projetMetier.chercherProjetParMc(mc,nom);
  
			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<Projet>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrés");
				reponse = new Reponse<List<Projet>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Projet>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/projet/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, projetMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

////////////get photo par id d'une travaux
	@GetMapping("/getPhoto/{idProjet}")
	public String getAbonnementByIdPersonne(@PathVariable("idProjet") long idProjet)
			throws JsonProcessingException, InvalideOryzException {
		Reponse<List<Photo>> reponse;
		try {
			List<Photo> photos = photoMetier.findPhotoByIdProjet(idProjet);
			if (!photos.isEmpty()) {
				reponse = new Reponse<List<Photo>>(0, null, photos);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de photos enregistrées");
				reponse = new Reponse<List<Photo>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Photo>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
@GetMapping("/projetByIdClient/{id}")
public String travauxByIdClient(@PathVariable Long id) throws JsonProcessingException {
	Reponse<List<Projet>> reponse;
	try {
		List<Projet> travaux = projetMetier.findProjetByIdClient(id);

		if (!travaux.isEmpty()) {
			reponse = new Reponse<List<Projet>>(0, null, travaux);
		} else {
			List<String> messages = new ArrayList<>();
			messages.add("Pas de travail info enregistrés");
			reponse = new Reponse<List<Projet>>(2, messages, new ArrayList<>());
		}

	} catch (Exception e) {
		reponse = new Reponse<List<Projet>>(1, Static.getErreursForException(e), new ArrayList<>());
	}
	return jsonMapper.writeValueAsString(reponse);

}

	
}
