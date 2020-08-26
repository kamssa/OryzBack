package ci.gestion.dao.detail;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.transport.DetailTransport;

public interface DetailTransportRepository extends JpaRepository<DetailTransport, Long>{

}
