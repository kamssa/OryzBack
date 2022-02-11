package ci.gestion.metier.combo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.MaterielRepository;
import ci.gestion.entites.operation.Materiaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MateriauxMetierImpl implements MateriauxMetier{
private MaterielRepository materielRepository;

@Override
public Materiaux creer(Materiaux entity) throws InvalideOryzException {

	if ((entity.getLibelle().equals(null)) || (entity.getLibelle() == "")) {
		throw new InvalideOryzException("Le libelle ne peut etre null");
	}
	
	Optional<Materiaux> cat = null;

	cat = materielRepository.findByLibelle(entity.getLibelle());
	if (cat.isPresent()) {
		throw new InvalideOryzException("Ce libelle est deja utilise");
	}
	return materielRepository.save(entity);
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
