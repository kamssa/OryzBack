package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;

public interface DetailAutreAchatTravauxRepository extends JpaRepository<DetailAutreAchatTravaux, Long>{
	@Query("select detailAutreAchatTravaux from DetailAutreAchatTravaux detailAutreAchatTravaux  where detailAutreAchatTravaux.projetId=?1")
	List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdProjet(long id);
	@Query("select detailAutreAchatTravaux from DetailAutreAchatTravaux detailAutreAchatTravaux  where detailAutreAchatTravaux.projetId=?1")
	List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxMontantByIdProjet(long id);
	// @Query("select t from DetailAutreAchatTravaux t where t.travauxId =?1 t.date BETWEEN :date AND :endDate")
           List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByDateBetweenAndProjetId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("projetId") Long projetId);
}
