package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.site.Site;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.travaux.ISiteMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SiteController {
	@Autowired
	private ISiteMetier siteMetier;
	@Autowired
	private ObjectMapper jsonMapper;

	// recuper travaux par identifiant
	private Reponse<Site> getSiteById(Long id) {
		Site site = null;

		try {
			site = siteMetier.findById(id);
			if (site == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("Le site n'existe pas", id));
				new Reponse<Site>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Site>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Site>(0, null, site);
	}

////////recuperer travail  par son id
	@GetMapping("/site/{id}")
	public String chercherSiteById(@PathVariable Long id) throws JsonProcessingException {
		// Annotation @PathVariable permet de recuperer le paremettre dans URI
		Reponse<Site> reponse = null;

		reponse = getSiteById(id);
		if (reponse.getBody() == null) {
			throw new RuntimeException("pas d'enregistrement pour ce site");
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	@PostMapping("/site")
	public String creer(@RequestBody Site site) throws JsonProcessingException {
		Reponse<Site> reponse;
		System.out.println(site);
		try {

			Site d = siteMetier.creer(site);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", d.getId()));
			reponse = new Reponse<Site>(0, messages, d);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Site>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@DeleteMapping("/site/{id}")
	public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

		Reponse<Boolean> reponse = null;

		try {

			List<String> messages = new ArrayList<>();
			messages.add(String.format(" %s  a ete supprime", true));

			reponse = new Reponse<Boolean>(0, messages, siteMetier.supprimer(id));

		} catch (RuntimeException e1) {
			reponse = new Reponse<>(3, Static.getErreursForException(e1), false);
		}

		return jsonMapper.writeValueAsString(reponse);
	}

}
