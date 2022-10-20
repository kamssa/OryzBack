package ci.gestion.dao.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.vehicule.Carburant;


public interface CarburantRepository extends JpaRepository<Carburant, Long>{
	
	@Query("select carburant from Carburant carburant where carburant.entreprise.id=?1")
	List<Carburant> getCarburantByEntreprise(long id);
	@Query("select carburant from Carburant carburant where carburant.idVehicule=?1")
	List<Carburant> getCarburantVehicule(long id);
	
	 
	 
	  List<Carburant>
	  findCarburantByDateBetweenAndEntreprise(
	  
	  @Param("date") LocalDate date,
	  
	  @Param("endDate") LocalDate endDate,
	  
	  @Param("entreprise") Entreprise entreprise);
	 
}
