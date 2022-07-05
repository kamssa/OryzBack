package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.entites.versement.Versement;



public interface DetailVersementRepository extends JpaRepository<DetailVersement, Long>{
	@Query("select dv from DetailVersement dv  where dv.travaux.id=?1")
	List<DetailVersement> findDetailVersementByIdTravaux(long id);
	
}
