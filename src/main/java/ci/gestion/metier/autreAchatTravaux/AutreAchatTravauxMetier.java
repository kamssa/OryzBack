package ci.gestion.metier.autreAchatTravaux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.query.Param;

import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface AutreAchatTravauxMetier extends Imetier<AutreAchatTravaux, Long>{
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdProjet(long id);
	List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdProjet(long id);
	Double findDetailAutreAchatTravauxMontantByIdProjet(long id);
	Double findAutreAchatTravauxMontantByIdProjet(long id);
    List<DetailAutreAchatTravaux>  getDetailAutreAchatTravauxBydate(long projetId,LocalDate startDate, LocalDate endDate);
    List<AutreAchatTravaux>  getAutreAchatTravauxBydate(long projetId,LocalDate startDate, LocalDate endDate);

	AutreAchatTravaux chercherAutreAchatTravauxParMc(String numeroFacture, long projetId);

}