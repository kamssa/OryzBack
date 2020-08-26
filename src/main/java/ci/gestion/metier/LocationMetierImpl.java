package ci.gestion.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.LocationRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailLcationRepository;
import ci.gestion.entites.Travaux;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.metier.exception.InvalideOryzException;
@Service
public class LocationMetierImpl implements ILocationMetier{
@Autowired
private LocationRepository locationRepository;
@Autowired
private DetailLcationRepository detailLcationRepository;
@Autowired
private TravauxRepository travauxRepository;

@Override
public LocationTravaux creer(LocationTravaux entity) throws InvalideOryzException {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	List<DetailLocation> detailLocations = entity.getDetailLocation();
	for(DetailLocation detail : detailLocations) {
		motantD += detail.getMontant();
		detail.setMontant(motantD);
		}
	entity.setMontant(motantD);
	LocationTravaux location= locationRepository.save(entity);
	Travaux travaux = travauxRepository.findById(location.getTravauxId()).get();
    montantTravaux = travaux.getTotal();
	montantT = montantTravaux + location.getMontant();
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
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
	return false;
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
public List<LocationTravaux> findLocationByIdTravaux(long id) {
	return locationRepository.findLocationByIdTravaux(id);
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
	 detailLcationRepository.deleteById(idDetail);
	 LocationTravaux locationTravaux = findById(idLocation);
	 montantLocationTravaux = locationTravaux.getMontant();
	 
     Travaux travaux = travauxRepository.findById(locationTravaux.getTravauxId()).get();
	 montantTravaux = travaux.getTotal();
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
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
	 return true;
}


}
