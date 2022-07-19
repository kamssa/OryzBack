package ci.gestion.metier.achatTravaux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface IAchatTravauxMetier extends Imetier<AchatTravaux, Long>{
	List<AchatTravaux> findAchatByIdProjet(long id);
    boolean supprimerDetailAchat(Long idAchat, Long idDetail);
    List<DetailAchatTravaux> findDetailAchatTravauxByIdProjet(long id);
	Double findDetailAchatTravauxMontantByIdProjet(long id);
	List<DetailAchatTravaux> findDetailAchatTravauxByDateIdProjet(long id, LocalDate dateDebut, LocalDate dateFin);

}
