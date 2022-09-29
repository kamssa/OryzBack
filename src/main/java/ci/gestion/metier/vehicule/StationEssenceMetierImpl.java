package ci.gestion.metier.vehicule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.vehicule.StationEssenceRepository;
import ci.gestion.entites.vehicule.StationEssence;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class StationEssenceMetierImpl implements StationEssenceMetier{
	@Autowired
	private StationEssenceRepository stationEssenceRepository;
	@Override
	public StationEssence creer(StationEssence entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return stationEssenceRepository.save(entity);
	}

	@Override
	public StationEssence modifier(StationEssence entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return stationEssenceRepository.save(entity);
	}

	@Override
	public List<StationEssence> findAll() {
		
		return stationEssenceRepository.findAll();
	}

	@Override
	public StationEssence findById(Long id) {
		// TODO Auto-generated method stub
		return stationEssenceRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		stationEssenceRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<StationEssence> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<StationEssence> getStationEssenceByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return stationEssenceRepository.getStationEssenceByIdEntreprise(id);
	}

}
