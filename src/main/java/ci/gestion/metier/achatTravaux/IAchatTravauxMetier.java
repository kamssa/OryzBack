package ci.gestion.metier.achatTravaux;

import java.util.List;

import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface IAchatTravauxMetier extends Imetier<AchatTravaux, Long>{
	List<AchatTravaux> findAchatByIdTravaux(long id);
    boolean supprimerDetailAchat(Long idAchat, Long idDetail);
    List<DetailAchatTravaux> findDetailAchatTravauxByIdTravaux(long id);
}
