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

import ci.gestion.entites.site.Photo;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.image.IPhotoMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.travaux.ITravauxMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TravauxController {
	@Autowired
	private ITravauxMetier travauxMetier;
	@Autowired
	private IPhotoMetier photoMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	

// recuper travaux par identifiant
	private Reponse<Travaux> getTravauxById(Long id) {
		Travaux travaux = null;

		try {
			travaux = travauxMetier.findById(id);
			if (travaux == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("le travail n'existe pas", id));
				new Reponse<Travaux>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Travaux>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Travaux>(0, null, travaux);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un travail dans  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/travaux")
	public String creer(@RequestBody Travaux travaux) throws JsonProcessingException {
		Reponse<Travaux> reponse;

		try {

			Travaux t1 = travauxMetier.creer(travaux);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Travaux>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Travaux>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}

/////////////////////////////////////////////////////////////////////////////////////////
// modifier un travail dans la base de donnee
///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

	@PutMapping("/travaux")
	public String modifier(@RequestBody Travaux modif) throws JsonProcessingException {
		Reponse<Travaux> reponsePersModif = null;
		Reponse<Travaux> reponse = null;

		// on recupere le flash info a modifier
		reponsePersModif = getTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				Travaux t2 = travauxMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", t2.getId()));
				reponse = new Reponse<Travaux>(0, messages, t2);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Travaux>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le travail n'existe pas"));
			reponse = new Reponse<Travaux>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}

///////////////////////////////////////////////////////////////////////////////////////////////
// recuperer tous les Travaux  de la base de
/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
	@GetMapping("/travaux")
	public String findAllTrauxInfo() throws JsonProcessingException, InvalideOryzException {
		Reponse<List<Travaux>> reponse;
		try {
			List<Travaux> flashs = travauxMetier.findAll();

			if (!flashs.isEmpty()) {
				reponse = new Reponse<List<Travaux>>(0, null, flashs);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrées");
				reponse = new Reponse<List<Travaux>>(1, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Travaux>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
	@GetMapping("/travaux/{id}")
	public String chercherTravauxById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Travaux> reponse = null;

		reponse = getTravauxById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour ce travail");
		}

		return jsonMapper.writeValueAsString(reponse);

	}

////////recuperer travail  par son id
	@GetMapping("/travauxByIdSite/{id}")
	public String travauxByIdSite(@PathVariable Long id) throws JsonProcessingException {
		Reponse<List<Travaux>> reponse;
		try {
			List<Travaux> travaux = travauxMetier.findTravauxByIdSite(id);

			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<Travaux>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrés");
				reponse = new Reponse<List<Travaux>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Travaux>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

////////rechercher un travail par mot cle
	@GetMapping("/rechemc")
	public String chercherTravauxByMc(@RequestParam(value = "mc") String mc, @RequestParam(value = "nom") String nom) throws JsonProcessingException {

		Reponse<List<Travaux>> reponse;
		try {
			List<Travaux> travaux = travauxMetier.chercherTravauxParMc(mc,nom);
  
			if (!travaux.isEmpty()) {
				reponse = new Reponse<List<Travaux>>(0, null, travaux);
			} else {
				List<String> messages = new ArrayList<>();
				messages.add("Pas de travail info enregistrés");
				reponse = new Reponse<List<Travaux>>(2, messages, new ArrayList<>());
			}

		} catch (Exception e) {
			reponse = new Reponse<List<Travaux>>(1, Static.getErreursForException(e), new ArrayList<>());
		}
		return jsonMapper.writeValueAsString(reponse);

	}

	@DeleteMapping("/travaux/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, travauxMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

////////////get photo par id d'une travaux
	@GetMapping("/getPhoto/{idTravaux}")
	public String getAbonnementByIdPersonne(@PathVariable("idTravaux") long idTravaux)
			throws JsonProcessingException, InvalideOryzException {
		Reponse<List<Photo>> reponse;
		try {
			List<Photo> photos = photoMetier.findPhotoByIdTravaux(idTravaux);
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
@GetMapping("/travauxByIdClient/{id}")
public String travauxByIdClient(@PathVariable Long id) throws JsonProcessingException {
	Reponse<List<Travaux>> reponse;
	try {
		List<Travaux> travaux = travauxMetier.findTravauxByIdClient(id);

		if (!travaux.isEmpty()) {
			reponse = new Reponse<List<Travaux>>(0, null, travaux);
		} else {
			List<String> messages = new ArrayList<>();
			messages.add("Pas de travail info enregistrés");
			reponse = new Reponse<List<Travaux>>(2, messages, new ArrayList<>());
		}

	} catch (Exception e) {
		reponse = new Reponse<List<Travaux>>(1, Static.getErreursForException(e), new ArrayList<>());
	}
	return jsonMapper.writeValueAsString(reponse);

}

	
}
