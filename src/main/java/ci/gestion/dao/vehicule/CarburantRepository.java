package ci.gestion.dao.vehicule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.Vehicule;


public interface CarburantRepository extends JpaRepository<Carburant, Long>{
	
	  List<Carburant> findCarburantByDateBetweenAndVehicule(
	 @Param("vehicle") Vehicule vehicle,
	 @Param("date") LocalDate date,
	 @Param("endDate") LocalDate endDate); 
	 
	  List<Carburant>
	  findCarburantByDateBetweenAndEntreprise(
	  
	  @Param("date") LocalDate date,
	  
	  @Param("endDate") LocalDate endDate,
	  
	  @Param("entreprise") Entreprise entreprise);
	 
}
