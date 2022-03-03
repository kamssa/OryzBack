package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.mainoeuvre.MainOeuvre;

public interface AutreRepository extends JpaRepository<Autres, Long>{
	@Query("select autres from Autres autres  where autres.travauxId=?1")
	List<Autres> findAutresByIdTravaux(long id);
}
