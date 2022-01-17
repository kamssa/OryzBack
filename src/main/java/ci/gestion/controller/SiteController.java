package ci.gestion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.site.Site;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.ISiteMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;


@RestController
@CrossOrigin
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
	
	
}
