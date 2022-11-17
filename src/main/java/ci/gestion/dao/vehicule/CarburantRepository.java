package ci.gestion.dao.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.vehicule.Prestation;


public interface CarburantRepository extends JpaRepository<Prestation, Long>{
	
	@Query("select prestation from Prestation prestation where prestation.entreprise.id=?1")
	List<Prestation> getCarburantByEntreprise(long id);
	@Query("select prestation from Prestation prestation where prestation.vehicule.id=?1")
	List<Prestation> getCarburantVehicule(long id);
	
	List<Prestation> findCarburantByDateBetweenAndVehicule(
			
            @Param("date") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("vehiculeId") long vehiculeId);
	 
List<Prestation> findPrestationByDateBetweenAndEntreprise(
			
            @Param("date") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("id") long id);
	 
}
