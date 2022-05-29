package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.location.DetailLocation;

public interface DetailLcationRepository extends JpaRepository<DetailLocation, Long>{
	@Query("select detailLocation from DetailLocation detailLocation  where detailLocation.travauxId=?1")
	List<DetailLocation> findDetailLocationByIdTravaux(long id);
	@Query("select detailLocation from DetailLocation detailLocation  where detailLocation.travauxId=?1")
	List<DetailLocation> findDetailLocationMontantByIdTravaux(long id);
}
