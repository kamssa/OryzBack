package ci.gestion.metier.location;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.LocationRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.detail.DetailLcationRepository;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.projet.Projet;
import ci.gestion.metier.exception.InvalideOryzException;
@Service
public class LocationMetierImpl implements ILocationMetier{
@Autowired
private LocationRepository locationRepository;
@Autowired
private DetailLcationRepository detailLcationRepository;
@Autowired
private ProjetRepository projetRepository;

@Override
public LocationTravaux creer(LocationTravaux entity) throws InvalideOryzException {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	List<DetailLocation> detailLocations = entity.getDetailLocation();
	for(DetailLocation detail : detailLocations) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		detail.setProjetId(entity.getProjetId());
		}
	entity.setMontant(motantD);
	LocationTravaux location= locationRepository.save(entity);
	Projet projet = projetRepository.findById(location.getProjetId()).get();
	 montantTravaux = projet.getTotal();
		montantT = montantTravaux + location.getMontant();
		projet.setTotal(montantT);
		Projet pr =projetRepository.save(projet);
			reste = (pr.getDebousserSec())-(pr.getTotal());
			pr.setReste(reste);
			double percent = 100*(pr.getTotal()/pr.getDebousserSec());
			pr.setPercent(percent);
			projetRepository.save(pr);
	 return location;
	 
    }



@Override
public LocationTravaux modifier(LocationTravaux entity) throws InvalideOryzException {
	return locationRepository.save(entity);
}

@Override
public List<LocationTravaux> findAll() {
	return locationRepository.findAll();
}

@Override
public LocationTravaux findById(Long id) {
	return locationRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	LocationTravaux locationTravaux = findById(id);
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	Projet projet = projetRepository.findById(locationTravaux.getProjetId()).get();
	montantTravaux = projet.getTotal();
	montantT = montantTravaux - locationTravaux.getMontant();
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = pr.getReste() + locationTravaux.getMontant();
	pr.setReste(reste);
	double percent = (pr.getDebousserSec()*pr.getTotal())/100;
	pr.setPercent(percent);
	projetRepository.save(pr);
	locationRepository.deleteById(id);
            return true; 
}

@Override
public boolean supprimer(List<LocationTravaux> entites) {
	return false;
}

@Override
public boolean existe(Long id) {
	return false;
}

@Override
public List<LocationTravaux> findLocationByIdProjet(long id) {
	return locationRepository.findLocationByIdProjet(id);
}



@Override
public boolean supprimerDetailLocation(Long idLocation, Long idDetail) {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double sommeMontant = 0;
	double montantLocationTravaux = 0;
	double montant = 0;
	double reste=0;
	double montant1 = 0;
	 detailLcationRepository.deleteById(idDetail);
	 LocationTravaux locationTravaux = findById(idLocation);
	 montantLocationTravaux = locationTravaux.getMontant();
	 
     Projet projet = projetRepository.findById(locationTravaux.getProjetId()).get();
	 montantTravaux = projet.getTotal();
	 montant = montantTravaux - montantLocationTravaux;
	
   	List<DetailLocation> detailLocations = locationTravaux.getDetailLocation();
	 
     for(DetailLocation detail : detailLocations) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		sommeMontant += motantD;
		System.out.println("Voir detail" + detail);
		}
	locationTravaux.setMontant(sommeMontant);
	LocationTravaux location= locationRepository.save(locationTravaux);
   
	montantT = montant + location.getMontant();
	projet.setTotal(montantT);
	Projet pr =projetRepository.save(projet);
	reste = (pr.getMontantFacture())-(pr.getTotal());
	       pr.setReste(reste);
	       projetRepository.save(pr);
	       LocationTravaux location1= locationRepository.findById(location.getId()).get();
			  montant1 = location1.getMontant();
			  if (montant1 == 0) {
				locationRepository.deleteById(location1.getId());
			}
			 return true;
}



@Override
public List<DetailLocation> findDetailLocationByIdProjet(long id) {
	// TODO Auto-generated method stub
	return detailLcationRepository.findDetailLocationByIdProjet(id);
}



@Override
public Double findDetailLocationMontantByIdProjet(long id) {
	double somme = 0d;
	List<DetailLocation> detailLocations = detailLcationRepository.findDetailLocationMontantByIdProjet(id);
	for (DetailLocation detailLocation : detailLocations) {
		somme += detailLocation.getMontant();
	}
	System.out.println("voir la somme"+ somme);
	// TODO Auto-generated method stub
	return somme;
}



@Override
public List<DetailLocation> getDetailLocationBydate(long id, LocalDate dateDebut, LocalDate dateFin) {
	  
	List<DetailLocation> detailLocations = detailLcationRepository.findDetailLocationByDateBetweenAndProjetId(dateDebut, dateFin, id);
	  
	  
	  return detailLocations;
}
	



}
