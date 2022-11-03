package ci.gestion.dao.vehicule;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.vehicule.PrestationStation;


public interface PrestationStationRepository extends JpaRepository<PrestationStation, Long>{

}
