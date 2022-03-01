package ci.gestion.metier.combo;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.CategorieRepository;
import ci.gestion.dao.MaterielRepository;
import ci.gestion.entites.operation.Categorie;
import ci.gestion.entites.operation.Materiaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MateriauxMetierImpl implements MateriauxMetier{
private MaterielRepository materielRepository;
CategorieRepository categorieRepository;

@Override
public Materiaux creer(Materiaux entity) throws InvalideOryzException {
	Materiaux mat= null;
	if ((entity.getLibelle().equals(null)) || (entity.getLibelle() == "")) {
		throw new InvalideOryzException("Le libelle ne peut etre null");
	}
	
	
	 List<Materiaux> mats = materielRepository.getMaterielByIdCategorie(entity.getCategorie().getId());
	if (mats.isEmpty()) {
		mat = materielRepository.save(entity);
	}else {
		 for (Materiaux materiaux : mats) {
				if (entity.getLibelle().equals(materiaux.getLibelle())) {
					throw new InvalideOryzException("Ce libelle est deja utilise");
				}else {
				 mat =	materielRepository.save(entity);
				}
			}
	}
	
	
	return mat;
}

@Override
public Materiaux modifier(Materiaux entity) throws InvalideOryzException {
	// TODO Auto-generated method stub
	return materielRepository.save(entity);
}

@Override
public List<Materiaux> findAll() {
	// TODO Auto-generated method stub
	return materielRepository.findAll();
}

@Override
public Materiaux findById(Long id) {
	// TODO Auto-generated method stub
	return materielRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	materielRepository.deleteById(id);
	return true;
}

@Override
public boolean supprimer(List<Materiaux> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<Materiaux> getMaterielByIdCategorie(long id) {
	// TODO Auto-generated method stub
	
	return materielRepository.getMaterielByIdCategorie(id);
}
	
}
