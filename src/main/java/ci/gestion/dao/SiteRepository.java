package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.site.Site;

public interface SiteRepository extends JpaRepository<Site, Long>{
 Site findByNomChantier(String nomChantier);
}
