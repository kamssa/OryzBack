package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ci.gestion.entites.transport.Transport;


public interface TransportRepository extends JpaRepository<Transport, Long>{
	@Query("select transport from Transport transport  where transport.travauxId=?1")
	List<Transport> findTransportByIdTravaux(long id);
}
