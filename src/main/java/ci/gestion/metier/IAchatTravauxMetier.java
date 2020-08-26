package ci.gestion.metier;

import java.util.List;

import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;

public interface IAchatTravauxMetier extends Imetier<AchatTravaux, Long>{
	List<AchatTravaux> findAchatByIdTravaux(long id);
    boolean supprimerDetailAchat(Long idAchat, Long idDetail);
}
