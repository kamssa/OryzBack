package ci.gestion.metier.vehicule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.vehicule.PrestationStationRepository;
import ci.gestion.entites.vehicule.PrestationStation;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class PrestationMetierImpl implements PrestationStatioMetier{
	@Autowired
	private PrestationStationRepository prestationStationRepository;
	@Override
	public PrestationStation creer(PrestationStation entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return prestationStationRepository.save(entity);
	}

	@Override
	public PrestationStation modifier(PrestationStation entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return prestationStationRepository.save(entity);
	}

	@Override
	public List<PrestationStation> findAll() {
		// TODO Auto-generated method stub
		 return prestationStationRepository.findAll();
	}

	@Override
	public PrestationStation findById(Long id) {
		// TODO Auto-generated method stub
		return prestationStationRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<PrestationStation> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
