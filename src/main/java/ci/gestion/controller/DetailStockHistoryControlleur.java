package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.DetailStockHistory;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.stock.DetailStockMetier;
import ci.gestion.metier.stockHistory.DetailStockHistoryMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DetailStockHistoryControlleur {
	@Autowired
	private DetailStockHistoryMetier detailStockHistoryMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<DetailStockHistory> getDetailStockById(Long id) {
		DetailStockHistory detailStock = null;

		try {
			detailStock = detailStockHistoryMetier.findById(id);
			if (detailStock == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le detailStock n'existe pas", id));
				new Reponse<DetailStock>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<DetailStockHistory>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<DetailStockHistory>(0, null, detailStock);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/detailStockHistory")
	public String creer(@RequestBody DetailStockHistory detailStock) throws JsonProcessingException {
		Reponse<DetailStockHistory> reponse;
		System.out.println(detailStock);
		try {

			DetailStockHistory cat = detailStockHistoryMetier.creer(detailStock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cat.getId()));
			reponse = new Reponse<DetailStockHistory>(0, messages, cat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<DetailStockHistory>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/detailStockHistory")
	public String update(@RequestBody DetailStockHistory modif) throws JsonProcessingException {

		Reponse<DetailStockHistory> reponse = null;
		Reponse<DetailStockHistory> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getDetailStockById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				DetailStockHistory detailStock = detailStockHistoryMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", detailStock.getId()));
				reponse = new Reponse<DetailStockHistory>(0, messages, detailStock);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<DetailStockHistory>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le DetailStock n'existe pas"));
			reponse = new Reponse<DetailStockHistory>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all detailStock
		@GetMapping("/detailStockHistory")
		public String findAll() throws JsonProcessingException {
			Reponse<List<DetailStockHistory>> reponse;
			try {
				List<DetailStockHistory> dtailStocks = detailStockHistoryMetier.findAll();
			
					reponse = new Reponse<List<DetailStockHistory>>(0, null, dtailStocks);
				 

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un departement par son identifiant
				@GetMapping("/detailStockHistory/{libelle}")
				public String getEntrepriseById(@PathVariable String  libelle) throws JsonProcessingException {
					Reponse<List<DetailStockHistory>> reponse;
					try {
						List<DetailStockHistory> db = detailStockHistoryMetier.findByLibelleMateriaux(libelle);
						reponse = new Reponse<List<DetailStockHistory>>(0, null, db);
					} catch (Exception e) {
						reponse = new Reponse<>(1, Static.getErreursForException(e), null);
					}
					return jsonMapper.writeValueAsString(reponse);

				}
}
