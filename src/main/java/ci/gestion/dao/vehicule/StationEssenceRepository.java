package ci.gestion.dao.vehicule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.vehicule.StationEssence;


public interface StationEssenceRepository extends JpaRepository<StationEssence, Long>{
	@Query("select station from StationEssence station where station.entreprise.id=?1")
	List<StationEssence> getStationEssenceByIdEntreprise(long id);
}
