package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.location.DetailLocation;

public interface DetailLcationRepository extends JpaRepository<DetailLocation, Long>{
	@Query("select detailLocation from DetailLocation detailLocation  where detailLocation.travauxId=?1")
	List<DetailLocation> findDetailLocationByIdTravaux(long id);
	@Query("select detailLocation from DetailLocation detailLocation  where detailLocation.travauxId=?1")
	List<DetailLocation> findDetailLocationMontantByIdTravaux(long id);
    List<DetailLocation> findDetailLocationByDateBetweenAndTravauxId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("travauxId") Long travauxId);
}
