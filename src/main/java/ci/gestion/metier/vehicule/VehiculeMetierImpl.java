package ci.gestion.metier.vehicule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.vehicule.CarburantRepository;
import ci.gestion.dao.vehicule.VehiculeRepository;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class VehiculeMetierImpl implements VehiculeMetier {
	@Autowired
	private VehiculeRepository vehiculeRepository;
	@Autowired
	private CarburantRepository carburantRepository;
	@Override
	public Vehicule creer(Vehicule entity) throws InvalideOryzException {
		return vehiculeRepository.save(entity);
	}

	@Override
	public Vehicule modifier(Vehicule entity) throws InvalideOryzException {
		return vehiculeRepository.save(entity);
	}

	@Override
	public List<Vehicule> findAll() {
		return vehiculeRepository.findAll();
	}

	@Override
	public Vehicule findById(Long id) {
		// TODO Auto-generated method stub
		return vehiculeRepository.findById(id).get();

	}

	@Override
	public boolean supprimer(Long id) {
		List<Carburant> carburants = carburantRepository.getCarburantVehicule(id);
		carburantRepository.deleteAll(carburants);
		vehiculeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Vehicule> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		vehiculeRepository.existsById(id);
		return true;
	}

	@Override
	public List<Vehicule> getVehiculeByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return vehiculeRepository.getVehiculeByIdEntreprise(id);
	}

}
