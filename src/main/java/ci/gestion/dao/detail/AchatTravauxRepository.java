package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.retraitStock.AchatTravaux;

public interface AchatTravauxRepository extends JpaRepository<AchatTravaux, Long>{
	@Query("select achatTravaux from AchatTravaux achatTravaux where achatTravaux.projetId=?1")
	List<AchatTravaux> getAchatTravauxByIdProjetId(long id);
	@Query("select achatTravaux from AchatTravaux achatTravaux where achatTravaux.libelle=?1")
	AchatTravaux getAchatTravauxBylibelle(String libelle);
	
}
