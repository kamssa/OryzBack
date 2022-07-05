package ci.gestion.metier.client;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.VersementRepository;
import ci.gestion.entites.site.Travaux;
import ci.gestion.entites.versement.Versement;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VersementMetierImpl implements VersementMetier{

	private VersementRepository versementRepository;
	private TravauxRepository travauxRepository;
	@Override
	public Versement creer(Versement entity) throws InvalideOryzException {
		Versement vers = null;
		double montantD = 0;
		double montantTotal = 0;
		Travaux tr;
		
		double reste = 0;
		
		
	    return null;
	
		
	}

	@Override
	public Versement modifier(Versement entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return  versementRepository.save(entity);
	}

	@Override
	public List<Versement> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Versement findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Versement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Versement findVersementByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return versementRepository.findVersementByIdTravaux(id).get();
	}

}
