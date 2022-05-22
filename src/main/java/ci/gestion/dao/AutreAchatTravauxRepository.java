package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.achat.AutreAchatTravaux;

public interface AutreAchatTravauxRepository extends JpaRepository<AutreAchatTravaux, Long> {
	@Query("select achatTravaux from AutreAchatTravaux achatTravaux where achatTravaux.travauxId=?1")
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdTravaux(long id);
}
