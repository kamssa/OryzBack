package ci.gestion.dao.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.vehicule.Carburant;


public interface CarburantRepository extends JpaRepository<Carburant, Long>{
	
	@Query("select carburant from Carburant carburant where carburant.entreprise.id=?1")
	List<Carburant> getCarburantByEntreprise(long id);
	@Query("select carburant from Carburant carburant where carburant.vehicule.id=?1")
	List<Carburant> getCarburantVehicule(long id);
	
	List<Carburant> findCarburantByDateBetweenAndVehicule(
			
            @Param("date") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("vehiculeId") long vehiculeId);
	 
	  
	 
}
