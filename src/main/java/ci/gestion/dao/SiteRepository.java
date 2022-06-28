package ci.gestion.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ci.gestion.entites.site.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {
	Optional<Site> findByNomChantier(String nomChantier);
	@Query("select site from Site site where site.entreprise.nom=?1")
	List<Site> siteParEntreprise(String nom);
}
