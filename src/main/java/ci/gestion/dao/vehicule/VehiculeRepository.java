package ci.gestion.dao.vehicule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.vehicule.Vehicule;


public interface VehiculeRepository extends JpaRepository<Vehicule, Long>{
	@Query("select v from Vehicule v where v.entreprise.id=?1")
	List<Vehicule> getVehiculeByIdEntreprise(long id);
}
