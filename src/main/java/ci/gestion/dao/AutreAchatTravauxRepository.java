package ci.gestion.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.projet.Projet;

public interface AutreAchatTravauxRepository extends JpaRepository<AutreAchatTravaux, Long> {
	@Query("select achatTravaux from AutreAchatTravaux achatTravaux where achatTravaux.projetId=?1")
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdProjet(long id);
	@Query("select pr from AutreAchatTravaux pr where pr.numeroFacture=?1  AND pr.projetId=?2")
	AutreAchatTravaux chercherAutreAchatTravauxParMc(@Param("numeroFacture") String numeroFacture, Long projetId);
	 List<AutreAchatTravaux> findAutreAchatTravauxByDateBetweenAndProjetId(
				
	            @Param("date") LocalDate date,
	            @Param("endDate") LocalDate endDate,
	            @Param("projetId") Long projetId);
}
