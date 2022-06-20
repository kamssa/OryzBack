package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.loyer.DetailLoyer;

public interface DetailLoyerRepository extends JpaRepository<DetailLoyer, Long>{
	@Query("select detailLoyer from DetailLoyer detailLoyer  where detailLoyer.travauxId=?1")
	List<DetailLoyer> findDetailLoyerByIdTravaux(long id);
	@Query("select detailLoyer from DetailLoyer detailLoyer  where detailLoyer.travauxId=?1")
	List<DetailLoyer>findDetailLoyerMontantByIdTravaux(long id);
    List<DetailLoyer> findDetailLoyerByDateBetweenAndTravauxId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("travauxId") Long travauxId);
}
