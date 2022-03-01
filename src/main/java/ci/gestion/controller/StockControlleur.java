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

import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.entites.operation.Materiaux;
import ci.gestion.metier.StockMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class StockControlleur {

	@Autowired
	private StockMetier stockMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper Departement par identifiant
	private Reponse<Stock> getStockById(Long id) {
		Stock stock = null;

		try {
			stock = stockMetier.findById(id);
			if (stock == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("stock n'existe pas", id));
				new Reponse<Stock>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Stock>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Stock>(0, null, stock);
	}

//////////////////////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un departement  dans la base de donnee
////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

	@PostMapping("/stock")
	public String creer(@RequestBody Stock stock) throws JsonProcessingException {
		Reponse<Stock> reponse;
		System.out.println(stock);
		try {

			Stock d = stockMetier.creer(stock);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Stock>(0, messages, d);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Stock>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/stock")
	public String update(@RequestBody Stock  modif) throws JsonProcessingException {

		Reponse<Stock> reponse = null;
		Reponse<Stock> reponsePersModif = null;
		// on recupere abonnement a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getStockById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Stock dep = stockMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", dep.getId()));
				reponse = new Reponse<Stock>(0, messages, dep);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Stock>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("Stock n'existe pas"));
			reponse = new Reponse<Stock>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// get all departement
		@GetMapping("/stock")
		public String findAll() throws JsonProcessingException {
			Reponse<List<Stock>> reponse;
			try {
				List<Stock> pers = stockMetier.findAll();
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Stock>>(0, null, pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de Stock enregistrés");
					reponse = new Reponse<List<Stock>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// obtenir un departement par son identifiant
		@GetMapping("/stock/{id}")
		public String getEntrepriseById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Stock> reponse;
			try {
				Stock db = stockMetier.findById(id);
				reponse = new Reponse<Stock>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		
		@DeleteMapping("/stock/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				List<String> messages = new ArrayList<>();
				messages.add(String.format(" %s  a ete supprime", true));

				reponse = new Reponse<Boolean>(0, messages, stockMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
		@GetMapping("/getStockByidEntreprise/{id}")
		public String getStockByEntreprise(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<Stock>> reponse = null;
			try {
				List<Stock> pers = stockMetier.getStockByIdEntreprise(id);
				reponse = new Reponse<List<Stock>>(0, null, pers);

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		@GetMapping("/listStockParEntreprise/{id}")
		public String getdetailstockByEntreprise(@PathVariable Long id) throws JsonProcessingException {
			Reponse<List<Stock>> reponse;
			try {
				List<Stock> pers = stockMetier.listStockParEntreprise(id);
				if (!pers.isEmpty()) {
					reponse = new Reponse<List<Stock>>(0, null, pers);
					System.out.println("list mat par entreprise:"+ pers);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de departement enregistrés");
					reponse = new Reponse<List<Stock>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
}
