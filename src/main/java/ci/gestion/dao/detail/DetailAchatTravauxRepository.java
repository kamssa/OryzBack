package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;


public interface DetailAchatTravauxRepository extends JpaRepository<DetailAchatTravaux, Long>{
	@Query("select ds from DetailAchatTravaux ds where ds.libelleMateriaux=?1")
	Optional<DetailAchatTravaux>  findByLibelleMateriaux(String libelleMateriaux);
	@Query("select detailAchatTravaux from DetailAchatTravaux detailAchatTravaux  where detailAchatTravaux.projetId=?1")
	Double findDetailAchatTravauxMontantByIdProjet(long id);
	@Query("select detailAchatTravaux from DetailAchatTravaux detailAchatTravaux  where detailAchatTravaux.projetId=?1")
	List<DetailAchatTravaux> findDetailAchatTravauxByIdProjet(long id);
	 List<DetailAchatTravaux> findDetailAchatTravauxByDateBetweenAndProjetId(
				
	            @Param("date") LocalDate date,
	            @Param("endDate") LocalDate endDate,
	            @Param("projetId") Long projetId);
}
