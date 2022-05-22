package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;

public interface DetailAutreAchatTravauxRepository extends JpaRepository<DetailAutreAchatTravaux, Long>{
	@Query("select detailAutreAchatTravaux from DetailAutreAchatTravaux detailAutreAchatTravaux  where detailAutreAchatTravaux.travauxId=?1")
	List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdTravaux(long id);
}
