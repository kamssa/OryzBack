package ci.gestion.metier.combo;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.CategorieRepository;
import ci.gestion.entites.combo.Categorie;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategorieMetierImpl implements CategorieMetier{
 CategorieRepository categorieRepository;
	@Override
	public Categorie creer(Categorie entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return categorieRepository.save(entity);
	}

	@Override
	public Categorie modifier(Categorie entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return categorieRepository.save(entity);
	}

	@Override
	public List<Categorie> findAll() {
		// TODO Auto-generated method stub
		return categorieRepository.findAll();
	}

	@Override
	public Categorie findById(Long id) {
		// TODO Auto-generated method stub
		return categorieRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		categorieRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Categorie> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
