package ci.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.versement.Versement;


public interface VersementRepository extends JpaRepository<Versement, Long>{
	@Query("select v from Versement v  where v.projet.id=?1")
	Optional<Versement> findVersementByIdProjet(long id);
}
