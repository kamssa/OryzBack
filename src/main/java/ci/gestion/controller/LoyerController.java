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

import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.loyer.ILoyerMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoyerController {
	@Autowired
	private ILoyerMetier loyerMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;

// recuper loyer par identifiant
	private Reponse<Loyer> getLoyerTravauxById(Long id) {
		Loyer loyer = null;

		try {
			loyer = loyerMetier.findById(id);
			if (loyer == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Loyer n'existe pas", id));
				new Reponse<Loyer>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Loyer>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Loyer>(0, null, loyer);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un loyer  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/loyer")
	public String creer(@RequestBody Loyer loyer) throws JsonProcessingException {
		Reponse<Loyer> reponse;
        System.out.println(loyer);
		try {

			Loyer t1 = loyerMetier.creer(loyer);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Loyer>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Loyer>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/loyer")
	public String update(@RequestBody Loyer  modif) throws JsonProcessingException {

		Reponse<Loyer> reponse = null;
		Reponse<Loyer> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getLoyerTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Loyer loyerTravaux = loyerMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", loyerTravaux.getId()));
				reponse = new Reponse<Loyer>(0, messages, loyerTravaux);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Loyer>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Autres n'existe pas"));
			reponse = new Reponse<Loyer>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer Loyer par id travaux
		@GetMapping("/loyer/{idTravaux}")
		public String getLoyerByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
			Reponse<List<Loyer>> reponse;

			try {
				List<Loyer> loyers = loyerMetier.findLoyerByIdTravaux(idTravaux);
				if (!loyers.isEmpty()) {
					reponse = new Reponse<List<Loyer>>(0, null, loyers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de loyer enregistré");
					reponse = new Reponse<List<Loyer>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {

				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir Loyer par son identifiant
		@GetMapping("/loye/{id}")
		public String getLoyerById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Loyer> reponse;
			try {
				Loyer db = loyerMetier.findById(id);
				reponse = new Reponse<Loyer>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer autre
		@DeleteMapping("/loyer/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, loyerMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
		@DeleteMapping("/DeleteDetailLoyer/{idLoyer}/{idDetail}")
		public String suppDetail(@PathVariable Long idLoyer, @PathVariable Long idDetail) throws JsonProcessingException {
			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, loyerMetier.supprimerDetailLoyer(idLoyer, idDetail));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		
		}
		// recuperer Detail loyer  par id travaux
					@GetMapping("/detailLoye/{idTravaux}")
					public String getDetailLocationByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
						Reponse<List<DetailLoyer>> reponse;

						try {
							List<DetailLoyer> mainOeuvres = loyerMetier.findDetailLoyerByIdTravaux(idTravaux);
							if (!mainOeuvres.isEmpty()) {
								reponse = new Reponse<List<DetailLoyer>>(0, null, mainOeuvres);
							} else {
								List<String> messages = new ArrayList<>();
								messages.add("Pas Autres enregistré");
								reponse = new Reponse<List<DetailLoyer>>(1, messages, new ArrayList<>());
							}

						} catch (Exception e) {

							reponse = new Reponse<>(1, Static.getErreursForException(e), null);
						}
						return jsonMapper.writeValueAsString(reponse);
					}
}
