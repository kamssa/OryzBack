package ci.gestion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import ci.gestion.entites.operation.Operation;
import ci.gestion.metier.IOperationBanqueMetier;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OperationBanqueController {
	@Autowired
	private IOperationBanqueMetier operationBanqueMetier;
	
	@Autowired
	private ObjectMapper jsonMapper;
	// recuper travaux par identifiant
		private Reponse<Operation> getOperationBanqueById(Long id) {
			Operation operation = null;

			try {
				operation = operationBanqueMetier.findById(id);
				if (operation == null) {
					List<String> messages = new ArrayList<>();
					messages.add(String.format("operation n'existe pas", id));
					new Reponse<Operation>(2, messages, null);

				}
			} catch (RuntimeException e) {
				new Reponse<Operation>(1, Static.getErreursForException(e), null);
			}

			return new Reponse<Operation>(0, null, operation);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////
	////////////////// enregistrer un Achat  dans la base de donnee
	////////////////////////////////////////////////////////////////////////////////////////////// donnee////////////////////////////////

		@PostMapping("/operation")
		public String creer(@RequestBody Operation op) throws JsonProcessingException {
			Reponse<Operation> reponse;
	        System.out.println(op);
			try {

				Operation t1 = operationBanqueMetier.creer(op);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s  à été créer avec succes", t1.getLibelle()));
				reponse = new Reponse<Operation>(0, messages, t1);

			} catch (InvalideOryzException e) {

				reponse = new Reponse<Operation>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
}
