package ci.gestion.metier.client;

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
@Autowired
private VersementRepository versementRepository;
@Autowired
private TravauxRepository travauxRepository;
@Transactional
@Override
public DetailVersement creer(DetailVersement entity) throws InvalideOryzException {
	double solde = 0;
	double budget = 0;
	double reste = 0;
	double montantVerse = 0;
	Optional<Versement> v = versementRepository.findVersementByIdTravaux(entity.getTravaux().getId());
	if (v.isPresent()) {
       solde = v.get().getSolde();
		
		solde += entity.getMontantVerse();
		v.get().setSolde(solde);
		Travaux tr = travauxRepository.findById(entity.getTravaux().getId()).get();
		budget = tr.getBudget();
		reste = budget - solde;
		v.get().setReste(reste);
		versementRepository.save(v.get());
		////////////////////////////////
		
	  
	}else {
		Versement vers = new Versement();
		vers.setTravaux(entity.getTravaux());
	  Versement	ve = versementRepository.save(vers);
		
	    solde = ve.getSolde();
		solde = entity.getMontantVerse();
		ve.setSolde(solde);
		Travaux tr = travauxRepository.findById(entity.getTravaux().getId()).get();
		budget = tr.getBudget();
		reste = budget - solde;
		ve.setReste(reste);
		versementRepository.save(ve);
	}
	return dversementRepository.save(entity);
}
@Override
public DetailVersement modifier(DetailVersement entity) throws InvalideOryzException {
	
	return null;
}
@Override
public List<DetailVersement> findAll() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public DetailVersement findById(Long id) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public boolean supprimer(Long id) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean supprimer(List<DetailVersement> entites) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public List<DetailVersement> detailVersementByIdVersement(Long id) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public List<DetailVersement> detailVersementByIdPersonne(Long id) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public List<DetailVersement> findDetailVersementByIdTravaux(long id) {
	// TODO Auto-generated method stub
	return dversementRepository.findDetailVersementByIdTravaux(id);
}

	
}
