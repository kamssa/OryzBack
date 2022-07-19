package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.location.LocationTravaux;

public interface LocationRepository extends JpaRepository<LocationTravaux, Long>{
	@Query("select location from LocationTravaux location  where location.projetId=?1")
	List<LocationTravaux> findLocationByIdProjet(long id);
}
