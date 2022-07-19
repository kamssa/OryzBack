package ci.gestion.metier.caisse;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.CaisseRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.entites.caisse.Caisse;
import ci.gestion.entites.caisse.CaisseDetail;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CaisseMetierImpl implements CaisseMetier{
private CaisseRepository caisseRepository;
private ProjetRepository projetRepository;

	@Override
	public Caisse creer(Caisse entity) throws InvalideOryzException {
		double montantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		List<CaisseDetail> caisseDetail = entity.getCaisseDetail();
		for(CaisseDetail detail : caisseDetail) {
			montantD = ((detail.getPrixUnitaire() * detail.getQuantite()));
			detail.setMontant(montantD);
			

			}
		entity.setMontant(montantD);
		Caisse autre= caisseRepository.save(entity);
	    
		return autre;
	}

	@Override
	public Caisse modifier(Caisse entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return caisseRepository.save(entity);
	}

	@Override
	public List<Caisse> findAll() {
		// TODO Auto-generated method stub
		return caisseRepository.findAll();
	}

	@Override
	public Caisse findById(Long id) {
		// TODO Auto-generated method stub
		return caisseRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		caisseRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Caisse> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
