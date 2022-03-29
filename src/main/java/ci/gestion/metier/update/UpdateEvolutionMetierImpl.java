package ci.gestion.metier.update;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreAchatTravauxRepository;
import ci.gestion.dao.AutreRepository;
import ci.gestion.dao.LocationRepository;
import ci.gestion.dao.LoyerRepository;
import ci.gestion.dao.MainDoeuvreRepository;
import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.TransportRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.AutreAchatTravaux;
import ci.gestion.entites.site.Travaux;
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
	private TravauxRepository travauxRepository;
	///////////////////////////////////////////////////////
	 public Travaux updateEvolution(Long id) {
		 
		 double montant = 0d;
		 double somme = 0d;
		 double reste;
		 
		List<AchatTravaux> achatstravaux = achatTravauxRepository.findAchatByIdTravaux(id);
		for (AchatTravaux achatTravaux : achatstravaux) {
			montant = achatTravaux.getMontant();
		    somme += montant;
		}
		List<AutreAchatTravaux> autreAchatTravauxautre = autreAchatTravauxRepository.getAutreAchatTravauxTravauxByIdTravaux(id);
		for (AutreAchatTravaux autreAchatTravaux : autreAchatTravauxautre) {
			montant = autreAchatTravaux.getMontant();
			somme += montant;
		}
		List<Autres> autres = autreRepository.findAutresByIdTravaux(id);
		for (Autres autre : autres) {
			montant = autre.getMontant();
			somme += montant;
		}
		List<LocationTravaux> locationTravaux = locationRepository.findLocationByIdTravaux(id);
		for (LocationTravaux locationTravau : locationTravaux) {
			montant = locationTravau.getMontant();
			somme += montant;
		}
		List<Loyer> loyers = loyerRepository.findLoyerByIdTravaux(id);
		for (Loyer loyer : loyers) {
			montant = loyer.getMontant();
			somme += montant;
		}
		List<MainOeuvre> mainOeuvres = mainDoeuvreRepository.findMainOeuvreByIdTravaux(id);
		for (MainOeuvre mainOeuvre : mainOeuvres) {
			montant = mainOeuvre.getMontant();
			somme += montant;
		}
		
		List<Transport> transports = transportRepository.findTransportByIdTravaux(id);
		for (Transport transport : transports) {
			montant = transport.getMontant();
			somme += montant;
		}
		Travaux travaux = travauxRepository.findById(id).get();
		
		travaux.setTotal(somme);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getDebousserSec())-(tr.getTotal());
		tr.setReste(reste);
		double percent = 100*(tr.getTotal()/tr.getDebousserSec());
		tr.setPercent(percent);
		Travaux trava = travauxRepository.save(tr);
		 return trava;
	 }
}
