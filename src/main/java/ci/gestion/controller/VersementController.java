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

import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.entites.versement.Versement;
import ci.gestion.metier.client.DetailVersementMetier;
import ci.gestion.metier.client.VersementMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class VersementController {
	@Autowired
	private DetailVersementMetier detailVersementMetier;
	@Autowired
	private VersementMetier versementMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;
	
	// recuper Ville par identifiant
		private Reponse<DetailVersement> getVersementById(Long id) {
			DetailVersement versement = null;

			try {
				 versement= detailVersementMetier.findById(id);
				if (versement == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("Versement n'existe pas", id));
					new Reponse<DetailVersement>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<DetailVersement>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<DetailVersement>(0, null, versement);
		}

		//////////////////////////////////////////////////////////////////////////////////////////////
		////////////////// enregistrer un versement dans la base de donnee
		////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/versement")
		public String creer(@RequestBody DetailVersement dversement) throws JsonProcessingException {
			Reponse<DetailVersement> reponse;
			System.out.println(dversement);
			try {

				DetailVersement v = detailVersementMetier.creer(dversement);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", v.getId()));
				reponse = new Reponse<DetailVersement>(0, messages, v);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<DetailVersement>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

		@PutMapping("/versement")
		public String update(@RequestBody DetailVersement modif) throws JsonProcessingException {

			Reponse<DetailVersement> reponse = null;
			Reponse<DetailVersement> reponseCatsModif = null;
			// on recupere autre a modifier
			System.out.println("modif recupere1:" + modif);
			reponseCatsModif = getVersementById(modif.getId());
			if (reponseCatsModif.getBody() != null) {
				try {
					System.out.println("modif recupere2:" + modif);
					DetailVersement v = detailVersementMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", v.getId()));
					reponse = new Reponse<DetailVersement>(0, messages, v);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<DetailVersement>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le versement n'existe pas"));
				reponse = new Reponse<DetailVersement>(3, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

		// recherche les Ville par id
		@GetMapping("/versement/{id}")
		public String getVersementByIdV(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<DetailVersement> reponse;

			try {

				DetailVersement v = detailVersementMetier.findById(id);
				List<String> messages = new ArrayList<>();
				messages.add(String.format(" à été recupere avec succes"));
				reponse = new Reponse<DetailVersement>(0, messages, v);

			} catch (Exception e) {

				reponse = new Reponse<DetailVersement>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

		
		// supprimer une ville
		@DeleteMapping("/versement/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, detailVersementMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		}

		// get all departement
		@GetMapping("/versement")
		public String findAll() throws JsonProcessingException {
			Reponse<List<DetailVersement>> reponse;
			try {
				List<DetailVersement> vs = detailVersementMetier.findAll();
				if (!vs.isEmpty()) {
					reponse = new Reponse<List<DetailVersement>>(0, null, vs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de categorie enregistrés");
					reponse = new Reponse<List<DetailVersement>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		
		// recherche une personne par son un mot cle
				@GetMapping("/versementByIdTravaux/{id}")
				public String getDVersementByPersonne(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<List<DetailVersement>> reponse;

					try {

						List<DetailVersement> v = detailVersementMetier.findDetailVersementByIdTravaux(id);
						List<String> messages = new ArrayList<>();
						messages.add(String.format(" versement effectué avec succes"));
						reponse = new Reponse<List<DetailVersement>>(0, messages, v);

					} catch (Exception e) {

						reponse = new Reponse<List<DetailVersement>>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);
				}
				// recherche une personne par son un mot cle
				@GetMapping("/vrsmentByIdTravaux/{id}")
				public String getSementByPersonne(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Versement> reponse;

					try {

						Versement v = versementMetier.findVersementByIdTravaux(id);
						List<String> messages = new ArrayList<>();
						messages.add(String.format(" versement effectué avec succes"));
						reponse = new Reponse<Versement>(0, messages, v);

					} catch (Exception e) {

						reponse = new Reponse<Versement>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);
				}
}
