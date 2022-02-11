package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.operation.Categorie;
import ci.gestion.metier.DetailStockMetier;
import ci.gestion.metier.combo.CategorieMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DetailStockController {
	@Autowired
	private DetailStockMetier detailStockMetier;

	@Autowired
	private ObjectMapper jsonMapper;

// recuper categorie par identifiant
	private Reponse<DetailStock> getDetailStockById(Long id) {
		DetailStock detailStock = null;

		try {
			detailStock = detailStockMetier.findById(id);
			if (detailStock == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le detailStock n'existe pas", id));
				new Reponse<DetailStock>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<DetailStock>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<DetailStock>(0, null, detailStock);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer une categories  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/detailStock")
	public String creer(@RequestBody DetailStock detailStock) throws JsonProcessingException {
		Reponse<DetailStock> reponse;
		System.out.println(detailStock);
		try {

			DetailStock cat = detailStockMetier.creer(detailStock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", cat.getId()));
			reponse = new Reponse<DetailStock>(0, messages, cat);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<DetailStock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/detailStock")
	public String update(@RequestBody DetailStock modif) throws JsonProcessingException {

		Reponse<DetailStock> reponse = null;
		Reponse<DetailStock> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:" + modif);
		reponsePersModif = getDetailStockById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:" + modif);
				DetailStock detailStock = detailStockMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", detailStock.getId()));
				reponse = new Reponse<DetailStock>(0, messages, detailStock);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<DetailStock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Le DetailStock n'existe pas"));
			reponse = new Reponse<DetailStock>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all detailStock
		@GetMapping("/detailStock")
		public String findAll() throws JsonProcessingException {
			Reponse<List<DetailStock>> reponse;
			try {
				List<DetailStock> dtailStocks = detailStockMetier.findAll();
			
					reponse = new Reponse<List<DetailStock>>(0, null, dtailStocks);
				 

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}

}
