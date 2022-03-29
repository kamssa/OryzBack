package ci.gestion.metier.travaux;

import java.util.List;

import ci.gestion.entites.site.Site;
import ci.gestion.metier.utilitaire.Imetier;

public interface ISiteMetier extends Imetier<Site, Long> {
	List<Site> siteParEntreprise(Long id);

}
