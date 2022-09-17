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

import ci.gestion.entites.banque.Banque;
import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.banque.IBanqueMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;
import ci.gestion.metier.vehicule.VehiculeMetier;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class VehiculeController {
	@Autowired
	private VehiculeMetier vehiculeMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	
	// recuper vehiculeMetier par identifiant
		private Reponse<Vehicule> getVehiculeById(Long id) {
			Vehicule vehicule = null;

			try {
				vehicule = vehiculeMetier.findById(id);
				if (vehicule == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("la banque n'existe pas", id));
					new Reponse<Vehicule>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Vehicule>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Vehicule>(0, null, vehicule);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un vehicule dans  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/vehicule")
		public String creer(@RequestBody Vehicule vehicule) throws JsonProcessingException {
			Reponse<Vehicule> reponse;

			try {
				Vehicule v = vehiculeMetier.creer(vehicule);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", v.getId()));
				reponse = new Reponse<Vehicule>(0, messages, v);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Vehicule>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}

	/////////////////////////////////////////////////////////////////////////////////////////
	// modifier une banque dans la base de donnee
	///////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////

		@PutMapping("/vehicule")
		public String modifier(@RequestBody Vehicule modif) throws JsonProcessingException {
			Reponse<Vehicule> reponsePersModif = null;
			Reponse<Vehicule> reponse = null;

			// on recupere le flash info a modifier
			reponsePersModif = getVehiculeById(modif.getId());
			if (reponsePersModif.getBody() != null) {
				try {
					Vehicule t2 = vehiculeMetier.modifier(modif);
					List<String> messages = new ArrayList<>();
					messages.add(String.format("%s a modifier avec succes", t2.getId()));
					reponse = new Reponse<Vehicule>(0, messages, t2);
				} catch (InvalideOryzException e) {

					reponse = new Reponse<Vehicule>(1, Static.getErreursForException(e), null);
				}

			} else {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le vehicule n'existe pas"));
				reponse = new Reponse<Vehicule>(0, messages, null);
			}

			return jsonMapper.writeValueAsString(reponse);

		}

	///////////////////////////////////////////////////////////////////////////////////////////////
	// recuperer toutes les banque  de la base de
	/////////////////////////////////////////////////////////////////////////////////////////////// donnee/////////////////////////////////////////
		@GetMapping("/vehicule")
		public String findAllBanque() throws JsonProcessingException, InvalideOryzException {
			Reponse<List<Vehicule>> reponse;
			try {
				List<Vehicule> flashs = vehiculeMetier.findAll();

				if (!flashs.isEmpty()) {
					reponse = new Reponse<List<Vehicule>>(0, null, flashs);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de vehicule info enregistrées");
					reponse = new Reponse<List<Vehicule>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<List<Vehicule>>(1, Static.getErreursForException(e), new ArrayList<>());
			}
			return jsonMapper.writeValueAsString(reponse);

		}

	////////recuperer travail  par son id
		@GetMapping("/vehicule/{id}")
		public String chercherBanqueById(@PathVariable Long id) throws JsonProcessingException {
			// Annotation @PathVariable permet de recuperer le paremettre dans URI
			Reponse<Vehicule> reponse = null;

			reponse = getVehiculeById(id);
			if (reponse.getBody() == null) {
				throw new RuntimeException("pas d'enregistrement pour ce vehicule");
			}

			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer un achat
				@DeleteMapping("/vehicule/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						reponse = new Reponse<Boolean>(0, null, vehiculeMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
					}
		 
					return jsonMapper.writeValueAsString(reponse);
				}
				@GetMapping("/getVehiculeByidEntreprise/{id}")
				public String getDepByEntreprise(@PathVariable Long id) throws JsonProcessingException {
					Reponse<List<Vehicule>> reponse;
					try {
						List<Vehicule> pers = vehiculeMetier.getVehiculeByIdEntreprise(id);
						if (!pers.isEmpty()) {
							reponse = new Reponse<List<Vehicule>>(0, null, pers);
						} else {
							List<String> messages = new ArrayList<>();
							messages.add("Pas de véhicule(s) enregistré(s)");
							reponse = new Reponse<List<Vehicule>>(1, messages, new ArrayList<>());
						}

					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}

}
