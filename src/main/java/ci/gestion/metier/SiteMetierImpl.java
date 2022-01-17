package ci.gestion.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.SiteRepository;
import ci.gestion.entites.site.Site;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class SiteMetierImpl implements ISiteMetier{
    
	@Autowired
	private SiteRepository siteRepository;
	
	@Override
	public Site creer(Site entity) throws InvalideOryzException {
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
		// TODO Auto-generated method stub
		return false;
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

}
