package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.loyer.Loyer;

public interface LoyerRepository extends JpaRepository<Loyer, Long>{
	@Query("select loyer from Loyer loyer  where loyer.projetId=?1")
	List<Loyer> findLoyerByIdProjet(long id);
}
