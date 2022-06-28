package ci.gestion.metier.travaux;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.SiteRepository;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.site.Site;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class SiteMetierImpl implements ISiteMetier{
    
	@Autowired
	private SiteRepository siteRepository;
	
	@Override
	public Site creer(Site entity) throws InvalideOryzException {
		System.out.println("personne a enregistrer" + ":" + entity);
		if ((entity.getNomChantier().equals(null)) || (entity.getNomChantier() == "")) {
			throw new InvalideOryzException("Le nom du projet ne peut etre null");
		}
		
		Optional<Site> site = null; 

		site = siteRepository.findByNomChantier(entity.getNomChantier());
		if (site.isPresent()) {
			throw new InvalideOryzException("Ce nom est déjà utilisé.");
		}

		return siteRepository.save(entity);
	}

	@Override
	public Site modifier(Site entity) throws InvalideOryzException {
		return siteRepository.save(entity);
	}

	@Override
	public List<Site> findAll() {
		return siteRepository.findAll();
	}

	@Override
	public Site findById(Long id) {
		return siteRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		siteRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Site> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Site> siteParEntreprise(String nom) {
		// TODO Auto-generated method stub
		return siteRepository.siteParEntreprise(nom);
	}

}
