package ci.gestion.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.entites.transport.DetailTransport;
import ci.gestion.entites.transport.Transport;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.mainOeuvre.IMainDoeuvreMetier;
import ci.gestion.metier.model.Reponse;
import ci.gestion.metier.transport.ITransportMetier;
import ci.gestion.metier.utilitaire.Static;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TransportController {
	@Autowired
	private ITransportMetier transportMetier;
	@Autowired
	private ObjectMapper jsonMapper;

// recuper mainOeuvre par identifiant
	private Reponse<Transport> getTransportTravauxById(Long id) {
		Transport transport = null;

		try {
			transport = transportMetier.findById(id);
			if (transport == null) {
				List<String> messages = new ArrayList<>();
				messages.add(String.format("transport n'existe pas", id));
				new Reponse<Transport>(2, messages, null);

			}
		} catch (RuntimeException e) {
			new Reponse<Transport>(1, Static.getErreursForException(e), null);
		}

		return new Reponse<Transport>(0, null, transport);
	}
////////////////////////////////////////////////////////////////////////////
////////////////// enregistrer un transport  dans la base
///////////////////////////////////////////////////////////////////////////

	@PostMapping("/transport")
	public String creer(@RequestBody Transport transport) throws JsonProcessingException {
		Reponse<Transport> reponse;
        System.out.println(transport);
		try {
			Transport t1 = transportMetier.creer(transport);
			List<String> messages = new ArrayList<>();
			messages.add(String.format("%s  à été créer avec succes", t1.getId()));
			reponse = new Reponse<Transport>(0, messages, t1);

		} catch (InvalideOryzException e) {

			reponse = new Reponse<Transport>(1, Static.getErreursForException(e), null);
		}
		return jsonMapper.writeValueAsString(reponse);
	}
	@PutMapping("/transport")
	public String update(@RequestBody Transport  modif) throws JsonProcessingException {

		Reponse<Transport> reponse = null;
		Reponse<Transport> reponsePersModif = null;
		// on recupere autre a modifier
		System.out.println("modif recupere1:"+ modif);
		reponsePersModif = getTransportTravauxById(modif.getId());
		if (reponsePersModif.getBody() != null) {
			try {
				System.out.println("modif recupere2:"+ modif);
				Transport transport = transportMetier.modifier(modif);
				List<String> messages = new ArrayList<>();
				messages.add(String.format("%s a modifier avec succes", transport.getId()));
				reponse = new Reponse<Transport>(0, messages, transport);
			} catch (InvalideOryzException e) {

				reponse = new Reponse<Transport>(1, Static.getErreursForException(e), null);
			}

		} else {
			List<String> messages = new ArrayList<>();
			messages.add(String.format("transport n'existe pas"));
			reponse = new Reponse<Transport>(0, messages, null);
		}

		return jsonMapper.writeValueAsString(reponse);

	}
	// recuperer transport par id travaux
		@GetMapping("/transport/{idTravaux}")
		public String getTransportByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
			Reponse<List<Transport>> reponse;

			try {
				List<Transport> autres = transportMetier.findTransportByIdTravaux(idTravaux);
				if (!autres.isEmpty()) {
					reponse = new Reponse<List<Transport>>(0, null, autres);
				} else {
					List<String> messages = new ArrayList<>();
					messages.add("Pas de  transport enregistré");
					reponse = new Reponse<List<Transport>>(1, messages, new ArrayList<>());
				}

			} catch (Exception e) {

				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);
		}
		// obtenir transport par son identifiant
		@GetMapping("/transports/{id}")
		public String getAutresById(@PathVariable Long id) throws JsonProcessingException {
			Reponse<Transport> reponse;
			try {
				Transport db = transportMetier.findById(id);
				reponse = new Reponse<Transport>(0, null, db);
			} catch (Exception e) {
				reponse = new Reponse<>(1, Static.getErreursForException(e), null);
			}
			return jsonMapper.writeValueAsString(reponse);

		}
		// supprimer autre
		@DeleteMapping("/transport/{id}")
		public String supprimer(@PathVariable("id") Long id) throws JsonProcessingException {

			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, transportMetier.supprimer(id));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		}
		@DeleteMapping("/DeleteDetailTransport/{idTransport}/{idDetail}")
		public String suppDetail(@PathVariable Long idTransport, @PathVariable Long idDetail) throws JsonProcessingException {
			Reponse<Boolean> reponse = null;

			try {

				reponse = new Reponse<Boolean>(0, null, transportMetier.supprimerDetailTransport(idTransport, idDetail));

			} catch (RuntimeException e1) {
				reponse = new Reponse<>(3, Static.getErreursForException(e1), null);
			}

			return jsonMapper.writeValueAsString(reponse);
		
		}
		// recuperer Detail transport  par id travaux
					@GetMapping("/detailTransport/{idTravaux}")
					public String getDetailTransportByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
						Reponse<List<DetailTransport>> reponse;

						try {
							List<DetailTransport> mainOeuvres = transportMetier.findDetailTransportByIdTravaux(idTravaux);
							if (!mainOeuvres.isEmpty()) {
								reponse = new Reponse<List<DetailTransport>>(0, null, mainOeuvres);
							} else {
								List<String> messages = new ArrayList<>();
								messages.add("Pas Autres enregistré");
								reponse = new Reponse<List<DetailTransport>>(1, messages, new ArrayList<>());
							}

						} catch (Exception e) {

							reponse = new Reponse<>(1, Static.getErreursForException(e), null);
						}
						return jsonMapper.writeValueAsString(reponse);
					}
					// recuperer achat par id travaux
					@GetMapping("/montantTransport/{idTravaux}")
					public String getmontantMainDoeuvreByIdTravaux(@PathVariable("idTravaux") long idTravaux) throws JsonProcessingException {
						Reponse<Double> reponse;
	                    
						try {
							Double achats = transportMetier.findDetailTransportMontantByIdTravaux(idTravaux);
							reponse = new Reponse<Double>(0, null, achats);
							System.out.println("voir la somme"+ achats);
						} catch (Exception e) {

							reponse = new Reponse<>(1, Static.getErreursForException(e), null);
						}
						return jsonMapper.writeValueAsString(reponse);
					}
					@GetMapping("/detailTransportDate")
					public String chercherTravauxByMc(
							@RequestParam(value = "travauxId") long travauxId,
							@RequestParam(value = "dateDebut") String dateDebut, 
					        @RequestParam(value = "dateFin") String dateFin) throws JsonProcessingException {
						System.out.println("Date bebut:"+ dateDebut);
						Reponse<List<DetailTransport>> reponse;
						//String str = "2022-05-31 00:00:00"; 
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate dateTime = LocalDate.parse(dateDebut, formatter);
						LocalDate dateTime1 = LocalDate.parse(dateFin, formatter);
						//LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, formatter);
						//LocalDateTime dateTime = LocalDateTime.parse(dateDebut);
						//LocalDateTime dateTime1 = LocalDateTime.parse(dateFin);
					   System.out.println("Date convertie:"+ dateTime);
					  // System.out.println("Date convertie1:"+ dateTime1);
						    //Next parse the date from the @RequestParam, specifying the TO type as 
						   
						try {
							List<DetailTransport> travaux = transportMetier.getDetailTransportBydate(travauxId,dateTime,dateTime1);
				  
							if (!travaux.isEmpty()) {
								reponse = new Reponse<List<DetailTransport>>(0, null, travaux);
							} else {
								List<String> messages = new ArrayList<>();
								messages.add("Pas d'enregistrement !");
								reponse = new Reponse<List<DetailTransport>>(2, messages, new ArrayList<>());
							}

						} catch (Exception e) {
							reponse = new Reponse<List<DetailTransport>>(1, Static.getErreursForException(e), new ArrayList<>());
						}
						return jsonMapper.writeValueAsString(reponse);

					}
}
