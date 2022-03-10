package ci.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.site.Site;

public interface SiteRepository extends JpaRepository<Site, Long>{
	Optional<Site> findByNomChantier(String nomChantier);
}
