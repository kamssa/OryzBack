package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.versement.DetailVersement;



public interface DetailVersementRepository extends JpaRepository<DetailVersement, Long>{
	@Query("select dv from DetailVersement dv  where dv.idVersement=?1")
	List<DetailVersement> findDetailVersementByidVersement(long id);
}
	

