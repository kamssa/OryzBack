package ci.gestion.controller.vehicule;

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

import ci.gestion.entites.vehicule.StationEssence;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;
import ci.gestion.metier.vehicule.StationEssenceMetier;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StationEssenceController {
	@Autowired
	private StationEssenceMetier stationEssenceMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuper vehiculeMetier par identifiant
		private Reponse<StationEssence> getStationEssenceById(Long id) {
			StationEssence stationEssence = null;

			try {
				stationEssence = stationEssenceMetier.findById(id);
				if (stationEssence == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("l'élément n'existe pas", id));
					new Reponse<StationEssence>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<StationEssence>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<StationEssence>(0, null, stationEssence);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un Carburant dans  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/stationEssence")
		public String creer(@RequestBody StationEssence stationEssence) throws JsonProcessingException {
			Reponse<StationEssence> reponse;

			try {
				StationEssence v = stationEssenceMetier.creer(stationEssence);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", v.getId()));
				reponse = new Reponse<StationEssence>(0, messages, v);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<StationEssence>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier une banque dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

		@PutMapping("/stationEssence")
		public String modifier(@RequestBody StationEssence modif) throws JsonProcessingException {
			Reponse<StationEssence> reponsePersModif = null;
			Reponse<StationEssence> reponse = null;

			// on recupere le flash info a modifier
			reponsePersModif = getStationEssenceById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					StationEssence t2 = stationEssenceMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", t2.getId()));
					reponse = new Reponse<StationEssence>(0, messages, t2);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<StationEssence>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le vehicule n'existe pas"));
				reponse = new Reponse<StationEssence>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer  Carburant  de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/stationEssence")
		public String findAllStationEssence() throws JsonProcessingException, InvalideOryzException {
			Reponse<List<StationEssence>> reponse;
			try {
				List<StationEssence> flashs = stationEssenceMetier.findAll();

				if (!flashs.isEmpty()) {
					reponse = new Reponse<List<StationEssence>>(0, null, flashs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de vehicule info enregistrées");
					reponse = new Reponse<List<StationEssence>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<List<StationEssence>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	////////recuperer travail  par son id
		@GetMapping("/stationEssence/{id}")
		public String chercherStationEssenceById(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<StationEssence> reponse = null;

			reponse = getStationEssenceById(id);
			if (reponse.getBody() == null) {
				throw new RuntimeException("pas d'enregistrement pour cette StationEssence");
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer un achat
				@DeleteMapping("/stationEssence/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						reponse = new Reponse<Boolean>(0, null, stationEssenceMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
		 
					return jsonMapper.writeValueAsString(reponse);
				}
				@GetMapping("/getStationEssenceByIdEntreprise/{id}")
				public String getDepByEntreprise(@PathVariable Long id) throws JsonProcessingException {
					Reponse<List<StationEssence>> reponse;
					try {
						List<StationEssence> pers = stationEssenceMetier.getStationEssenceByIdEntreprise(id);
						if (!pers.isEmpty()) {
							reponse = new Reponse<List<StationEssence>>(0, null, pers);
						} else {
							List<String> messages = new ArrayList<>();
							messages.add("Pas de Station(s) enregistré(s)");
							reponse = new Reponse<List<StationEssence>>(1, messages, new ArrayList<>());
						}

					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
			
}
