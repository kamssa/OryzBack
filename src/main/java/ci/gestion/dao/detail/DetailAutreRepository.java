package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;

public interface DetailAutreRepository extends JpaRepository<DetailAutres, Long>{
	@Query("select detailAutres from DetailAutres detailAutres  where detailAutres.travauxId=?1")
	List<DetailAutres> findDetailAutresByIdTravaux(long id);
	@Query("select detailAutres from DetailAutres detailAutres  where detailAutres.travauxId=?1")
	List<DetailAutres> findDetailAutresMontantByIdTravaux(long id);
	List<DetailAutres> findDetailAutresByDateBetweenAndTravauxId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("travauxId") Long travauxId);
	
}
