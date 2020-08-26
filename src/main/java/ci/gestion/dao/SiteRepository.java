package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.Site;

public interface SiteRepository extends JpaRepository<Site, Long>{
 Site findByNom(String nom);
}
