package ci.gestion.metier.personne;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.DepartementRepository;
import ci.gestion.entites.entreprise.Departement;
import ci.gestion.metier.exception.InvalideOryzException;



@Service
public class DepartementMetierImpl implements IDepartementMetier{
@Autowired
private DepartementRepository departementRepository;
	@Override
	public Departement creer(Departement entity) throws InvalideOryzException {
		if ((entity.getLibelle().equals(null)) || (entity.getLibelle() == "")) {
			throw new InvalideOryzException("Le libelle ne peut etre null");
		}
		List<Departement> deps =departementRepository.getDepByIdEntreprise(entity.getEntreprise().getId());
		for (Departement dep: deps) {
			String libelle = dep.getLibelle();
			List<String> lang = new ArrayList<>();
	         lang.add(libelle);
	         for (String i : lang) {
	             if (i.contains(entity.getLibelle())) {
	     			throw new InvalideOryzException("Ce département existe déjà");

	             }
	         }
			
		}
		
		return departementRepository.save(entity);
	}

	@Override
	public Departement modifier(Departement entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return departementRepository.save(entity);
	}

	@Override
	public List<Departement> findAll() {
		// TODO Auto-generated method stub
		return departementRepository.findAll();
	}

	@Override
	public Departement findById(Long id) {
		// TODO Auto-generated method stub
		return departementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		departementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Departement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Departement> getDepByIdEntreprise(Long id) {
		// TODO Auto-generated method stub
		return departementRepository.getDepByIdEntreprise(id);
	}

	@Override
	public Departement findDepartementByLibelle(String libelle) {
		return departementRepository.getDepByLibelle(libelle);
	}

	
}
