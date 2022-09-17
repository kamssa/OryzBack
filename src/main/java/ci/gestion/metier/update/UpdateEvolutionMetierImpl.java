package ci.gestion.metier.update;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreAchatTravauxRepository;
import ci.gestion.dao.AutreRepository;
import ci.gestion.dao.LocationRepository;
import ci.gestion.dao.LoyerRepository;
import ci.gestion.dao.MainDoeuvreRepository;
import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.TransportRepository;
import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.entites.projet.Projet;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.transport.Transport;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UpdateEvolutionMetierImpl {
	////////////////////////////////////////////////////
	private OperationTravauxRepository achatTravauxRepository;
	private AutreAchatTravauxRepository autreAchatTravauxRepository;
	private AutreRepository autreRepository;
	private LocationRepository locationRepository;
	private LoyerRepository loyerRepository;
	private MainDoeuvreRepository mainDoeuvreRepository;
	private TransportRepository transportRepository;
	private ProjetRepository projetRepository;
	///////////////////////////////////////////////////////
	 public Projet updateEvolution(Long id) {
		 
		 double montant = 0d;
		 double somme = 0d;
		 double reste;
		 
		List<AchatTravaux> achatstravaux = achatTravauxRepository.findAchatByIdProjet(id);
		for (AchatTravaux achatTravaux : achatstravaux) {
			montant = achatTravaux.getMontant();
		    somme += montant;
		}
		List<AutreAchatTravaux> autreAchatTravauxautre = autreAchatTravauxRepository.getAutreAchatTravauxTravauxByIdProjet(id);
		for (AutreAchatTravaux autreAchatTravaux : autreAchatTravauxautre) {
			montant = autreAchatTravaux.getMontant();
			somme += montant;
		}
		List<Autres> autres = autreRepository.findAutresByIdProjet(id);
		for (Autres autre : autres) {
			montant = autre.getMontant();
			somme += montant;
		}
		List<LocationTravaux> locationTravaux = locationRepository.findLocationByIdProjet(id);
		for (LocationTravaux locationTravau : locationTravaux) {
			montant = locationTravau.getMontant();
			somme += montant;
		}
		List<Loyer> loyers = loyerRepository.findLoyerByIdProjet(id);
		for (Loyer loyer : loyers) {
			montant = loyer.getMontant();
			somme += montant;
		}
		List<MainOeuvre> mainOeuvres = mainDoeuvreRepository.findMainOeuvreByIdProjet(id);
		for (MainOeuvre mainOeuvre : mainOeuvres) {
			montant = mainOeuvre.getMontant();
			somme += montant;
		}
		
		List<Transport> transports = transportRepository.findTransportByIdProjet(id);
		for (Transport transport : transports) {
			montant = transport.getMontant();
			somme += montant;
		}
		Projet projet = projetRepository.findById(id).get();
		
		projet.setTotal(somme);
		Projet pr = projetRepository.save(projet);
		reste = (pr.getDebousserSec())-(pr.getTotal());
		pr.setReste(reste);
		double percent = 100*(pr.getTotal()/pr.getDebousserSec());
		pr.setPercent(percent);
		Projet proj = projetRepository.save(pr);
		 return proj;
	 }
}
