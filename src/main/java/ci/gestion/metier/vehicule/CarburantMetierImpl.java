package ci.gestion.metier.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.vehicule.CarburantRepository;
import ci.gestion.dao.vehicule.PrestationStationRepository;
import ci.gestion.entites.vehicule.Prestation;
import ci.gestion.entites.vehicule.PrestationStation;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class CarburantMetierImpl implements CarburantMetier{
	@Autowired
	private CarburantRepository carburantRepository;
	@Autowired
    PrestationStationRepository prestationStationRepository;
	@Override
	public Prestation creer(Prestation entity) throws InvalideOryzException {
		
		PrestationStation prestationStation = null;
		double quantite = 0;
		double total = 0;
		String libelle = entity.getLibelle();
		if(libelle.equals("super")) {
			prestationStation =	prestationStationRepository.findByLibelle(libelle);
			 quantite = entity.getTotal() / prestationStation.getPrixSuper();
			 entity.setQuantite(quantite);
		        entity.setPrixUnitaire(prestationStation.getPrixSuper());
		        
			
		}else if(libelle.equals("gazoil")) {
			prestationStation =	prestationStationRepository.findByLibelle(libelle);
			 quantite = entity.getTotal() / prestationStation.getPrixGazoil();
			 entity.setQuantite(quantite);
		        entity.setPrixUnitaire(prestationStation.getPrixGazoil());
		}
		
        
		return carburantRepository.save(entity);
	}

	@Override
	public Prestation modifier(Prestation entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return carburantRepository.save(entity);
	}

	@Override
	public List<Prestation> findAll() {
		// TODO Auto-generated method stub
		return  carburantRepository.findAll();

	}

	@Override
	public Prestation findById(Long id) {
		// TODO Auto-generated method stub
		return carburantRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		carburantRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Prestation> entites) {
		carburantRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Prestation> getCarburantVehiculeParDate(long vehiculeId, LocalDate startDate, LocalDate endDate) {
		
		return carburantRepository.findCarburantByDateBetweenAndVehicule(endDate, startDate, vehiculeId);
	}

	@Override
	public List<Prestation> getCarburantByEntreprise(long id) {
		// TODO Auto-generated method stub
		return carburantRepository.getCarburantByEntreprise(id);
	}

	

}
