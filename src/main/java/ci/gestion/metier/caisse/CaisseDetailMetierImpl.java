package ci.gestion.metier.caisse;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.CaisseDetailRepository;
import ci.gestion.entites.caisse.CaisseDetail;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CaisseDetailMetierImpl implements CaisseDetailMetier{
private CaisseDetailRepository caisseDetailRepository;

@Override
public CaisseDetail creer(CaisseDetail entity) throws InvalideOryzException {
	// TODO Auto-generated method stub
	return caisseDetailRepository.save(entity);
}

@Override
public CaisseDetail modifier(CaisseDetail entity) throws InvalideOryzException {
	// TODO Auto-generated method stub
	return caisseDetailRepository.save(entity);
}

@Override
public List<CaisseDetail> findAll() {
	// TODO Auto-generated method stub
	return caisseDetailRepository.findAll();
}

@Override
public CaisseDetail findById(Long id) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean supprimer(List<CaisseDetail> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<CaisseDetail> findCaisseDetailByIdEntreprise(long id) {
	// TODO Auto-generated method stub
	return caisseDetailRepository.findCaisseDetailByIdEntreprise(id);
}


}
