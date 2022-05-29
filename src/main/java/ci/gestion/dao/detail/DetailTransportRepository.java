package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.transport.DetailTransport;

public interface DetailTransportRepository extends JpaRepository<DetailTransport, Long>{
	@Query("select detailTransport from DetailTransport detailTransport  where detailTransport.travauxId=?1")
	List<DetailTransport> findDetailTransportByIdTravaux(long id);
	@Query("select detailTransport from DetailTransport detailTransport  where detailTransport.travauxId=?1")
	List<DetailTransport> findDetailTransportMontantByIdTravaux(long id);
}
