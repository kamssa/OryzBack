package ci.gestion.metier.combo;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.MaterielRepository;
import ci.gestion.entites.combo.Categorie;
import ci.gestion.entites.combo.Materiel;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MaterielMetierImpl implements MaterielMetier{
private MaterielRepository materielRepository;
	@Override
	public Materiel creer(Materiel entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return materielRepository.save(entity);
	}

	@Override
	public Materiel modifier(Materiel entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return materielRepository.save(entity);
	}

	@Override
	public List<Materiel> findAll() {
		// TODO Auto-generated method stub
		return materielRepository.findAll();
	}

	@Override
	public Materiel findById(Long id) {
		// TODO Auto-generated method stub
		return materielRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		materielRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Materiel> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Materiel> getMaterielByIdCategorie(long id) {
		// TODO Auto-generated method stub
		return materielRepository.getMaterielByIdCategorie(id);
	}

}
