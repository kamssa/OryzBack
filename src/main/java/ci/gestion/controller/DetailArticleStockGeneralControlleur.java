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

import ci.gestion.entites.stock.DetailAticleStockGeneral;
import ci.gestion.entites.stock.DetailStock;
import ci.gestion.entites.stock.Stock;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.stock.DetailStockMetier;
import ci.gestion.metier.stockGeneral.DetailStockGeneralMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DetailArticleStockGeneralControlleur {
	@Autowired
	private DetailStockGeneralMetier detailStockGeneralMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<DetailAticleStockGeneral> getDetailAticleStockGeneralById(Long id) {
		DetailAticleStockGeneral detailStockGeneral = null;

		try {
			detailStockGeneral = detailStockGeneralMetier.findById(id);
			if (detailStockGeneral == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le detailStock n'existe pas", id));
				new Reponse<DetailStock>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<DetailAticleStockGeneral>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<DetailAticleStockGeneral>(0, null, detailStockGeneral);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/detailAticleStockGeneral")
	public String creer(@RequestBody DetailAticleStockGeneral detailStock) throws JsonProcessingException {
		Reponse<DetailAticleStockGeneral> reponse;
		System.out.println(detailStock);
		try {

			DetailAticleStockGeneral cat = detailStockGeneralMetier.creer(detailStock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cat.getId()));
			reponse = new Reponse<DetailAticleStockGeneral>(0, messages, cat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<DetailAticleStockGeneral>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/detailAticleStockGeneral")
	public String update(@RequestBody DetailAticleStockGeneral modif) throws JsonProcessingException {

		Reponse<DetailAticleStockGeneral> reponse = null;
		Reponse<DetailAticleStockGeneral> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getDetailAticleStockGeneralById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				DetailAticleStockGeneral detailStock = detailStockGeneralMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", detailStock.getId()));
				reponse = new Reponse<DetailAticleStockGeneral>(0, messages, detailStock);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<DetailAticleStockGeneral>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le DetailStock n'existe pas"));
			reponse = new Reponse<DetailAticleStockGeneral>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all detailStock
		@GetMapping("/detailAticleStockGeneral")
		public String findAll() throws JsonProcessingException {
			Reponse<List<DetailAticleStockGeneral>> reponse;
			try {
				List<DetailAticleStockGeneral> dtailStocks = detailStockGeneralMetier.findAll();
			
					reponse = new Reponse<List<DetailAticleStockGeneral>>(0, null, dtailStocks);
				 

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un DetailStock par son identifiant
				@GetMapping("/detailAticleStockGeneral/{id}")
				public String getEntrepriseById(@PathVariable Long id) throws JsonProcessingException {
					Reponse<DetailAticleStockGeneral> reponse;
					try {
						DetailAticleStockGeneral db = detailStockGeneralMetier.findById(id);
						reponse = new Reponse<DetailAticleStockGeneral>(0, null, db);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
				@DeleteMapping("/detailAticleStockGeneral/{id}")
				public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

					Reponse<Boolean> reponse = null;

					try {

						List<String> messages = new ArrayList<>();
						messages.add(String.format(" %s  a ete supprime", true));

						reponse = new Reponse<Boolean>(0, messages, detailStockGeneralMetier.supprimer(id));

					} catch (RuntimeException e1) {
						reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
					}

					return jsonMapper.writeValueAsString(reponse);
				}
				@GetMapping("/getStockGenralByidEntreprise/{id}")
				public String getStockGeneralByEntreprise(@PathVariable Long id) throws JsonProcessingException {
					Reponse<List<DetailAticleStockGeneral>> reponse = null;
					try {
						List<DetailAticleStockGeneral> pers = detailStockGeneralMetier.getDetailArticleStockGeneralByIdEntreprise(id);
						reponse = new Reponse<List<DetailAticleStockGeneral>>(0, null, pers);

					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}

}
