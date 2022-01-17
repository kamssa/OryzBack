package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.site.Travaux;

public interface TravauxRepository extends JpaRepository<Travaux, Long>{
	@Query("select travaux from Travaux travaux where travaux.site.nomChantier LIKE %?1%")
	List<Travaux> chercherTravauxParMc(@Param("nomChantier") String mc);
}
