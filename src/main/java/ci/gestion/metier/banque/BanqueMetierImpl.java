package ci.gestion.metier.banque;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.BanqueRepository;
import ci.gestion.entites.banque.Banque;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class BanqueMetierImpl implements IBanqueMetier{
@Autowired
private BanqueRepository banqueRepository;
	@Override
	public Banque creer(Banque entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return banqueRepository.save(entity);
	}

	@Override
	public Banque modifier(Banque entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return banqueRepository.save(entity);
	}

	@Override
	public List<Banque> findAll() {
		// TODO Auto-generated method stub
		return banqueRepository.findAll();
	}

	@Override
	public Banque findById(Long id) {
		// TODO Auto-generated method stub
		return banqueRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		banqueRepository.deleteById(id);	
		return true;
	}

	@Override
	public boolean supprimer(List<Banque> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Banque> getBanqueByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return banqueRepository.getBanqueByIdEntreprise(id);
	}

}
