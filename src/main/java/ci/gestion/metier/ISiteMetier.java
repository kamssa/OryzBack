package ci.gestion.metier;

import java.util.List;

import ci.gestion.entites.site.Site;

public interface ISiteMetier extends Imetier<Site, Long> {
	List<Site> siteParEntreprise(Long id);

}
