package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.projet.Projet;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.update.UpdateEvolutionMetierImpl;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UpdateEvolutionController {
	@Autowired
	private UpdateEvolutionMetierImpl updateEvolutionMetier;
	@Autowired
	private ObjectMapper jsonMapper;
	
	@GetMapping("/evolution/{id}")
	public String creer(@PathVariable Long id) throws JsonProcessingException, InvalideOryzException {
		Reponse<Projet> reponse;
       
		Projet t1 = updateEvolutionMetier.updateEvolution(id);
		List<String> messages = new ArrayList<>();
		messages.add(String.format("%s  à été créer avec succes", t1.getLibelle()));
		reponse = new Reponse<Projet>(0, messages, t1);
		return jsonMapper.writeValueAsString(reponse);
	}

}
