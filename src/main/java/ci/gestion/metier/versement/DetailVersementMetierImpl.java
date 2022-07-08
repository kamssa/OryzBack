package ci.gestion.metier.versement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.VersementRepository;
import ci.gestion.dao.detail.DetailVersementRepository;
import ci.gestion.entites.site.Travaux;
import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.entites.versement.Versement;
import ci.gestion.metier.exception.InvalideOryzException;


@Service
public class DetailVersementMetierImpl implements DetailVersementMetier{
@Autowired
private DetailVersementRepository dversementRepository;

@Transactional
@Override
public DetailVersement creer(DetailVersement entity) throws InvalideOryzException {
	return dversementRepository.save(entity);
	
	
}
@Override
public DetailVersement modifier(DetailVersement entity) throws InvalideOryzException {
	
	return dversementRepository.save(entity);
}
@Override
public List<DetailVersement> findAll() {
	return null;
}
@Override
public DetailVersement findById(Long id) {
	return dversementRepository.findById(id).get();
}
@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	dversementRepository.deleteById(id);
	return true;
}
@Override
public boolean supprimer(List<DetailVersement> entites) {
     dversementRepository.deleteAll(entites);
	return true;
}
@Override
public boolean existe(Long id) {
	dversementRepository.existsById(id);
	return true;
}
@Override
public List<DetailVersement> findDetailVersementByidVersement(long id) {
	// TODO Auto-generated method stub
	return dversementRepository.findDetailVersementByidVersement(id);
}


	
}
