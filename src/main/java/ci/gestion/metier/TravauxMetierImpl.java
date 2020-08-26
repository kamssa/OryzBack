package ci.gestion.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.TravauxRepository;
import ci.gestion.entites.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class TravauxMetierImpl implements ITravauxMetier{
@Autowired
private TravauxRepository travauxRepository;
	@Override
	public Travaux creer(Travaux entity) throws InvalideOryzException {
	
		return travauxRepository.save(entity);
	}

	@Override
	public Travaux modifier(Travaux entity) throws InvalideOryzException {
		
		return travauxRepository.save(entity);
	}

	@Override
	public List<Travaux> findAll() {
		
		return travauxRepository.findAll();
	}

	@Override
	public Travaux findById(Long id) {
		
		return travauxRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Travaux> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Travaux> chercherTravauxParMc(String mc) {
		// TODO Auto-generated method stub
		return travauxRepository.chercherTravauxParMc(mc);
	}

}
