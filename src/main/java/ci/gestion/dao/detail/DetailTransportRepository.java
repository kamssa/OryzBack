package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.transport.DetailTransport;

public interface DetailTransportRepository extends JpaRepository<DetailTransport, Long>{
	@Query("select detailTransport from DetailTransport detailTransport  where detailTransport.travauxId=?1")
	List<DetailTransport> findDetailTransportByIdTravaux(long id);
	@Query("select detailTransport from DetailTransport detailTransport  where detailTransport.travauxId=?1")
	List<DetailTransport> findDetailTransportMontantByIdTravaux(long id);
    List<DetailTransport> findDetailTransportByDateBetweenAndTravauxId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("travauxId") Long travauxId);
}
