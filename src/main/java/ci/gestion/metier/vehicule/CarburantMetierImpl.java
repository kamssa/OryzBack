package ci.gestion.metier.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.vehicule.CarburantRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class CarburantMetierImpl implements CarburantMetier{
	@Autowired
	private CarburantRepository carburantRepository;
	@Override
	public Carburant creer(Carburant entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		double total = 0;
		total = entity.getPrixUnitaire() * entity.getQuantite();
		entity.setTotal(total);
		return carburantRepository.save(entity);
	}

	@Override
	public Carburant modifier(Carburant entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return carburantRepository.save(entity);
	}

	@Override
	public List<Carburant> findAll() {
		// TODO Auto-generated method stub
		return  carburantRepository.findAll();

	}

	@Override
	public Carburant findById(Long id) {
		// TODO Auto-generated method stub
		return carburantRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		carburantRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Carburant> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public List<Carburant> findCarburantByDateBetweenAndVehicule(long idVehicule, LocalDate startDate,
			LocalDate endDate) {
		// TODO Auto-generated method stub

		return carburantRepository.findCarburantByDateBetweenAndVehicule(startDate, endDate, idVehicule);
	}

	@Override
	public List<Carburant> getCarburantVehicule(long id) {
		// TODO Auto-generated method stub
		return carburantRepository.getCarburantVehicule(id);
	}

	@Override
	public List<Carburant> getCarburantByEntreprise(long id) {
		// TODO Auto-generated method stub
		return carburantRepository.getCarburantByEntreprise(id);
	}

}
