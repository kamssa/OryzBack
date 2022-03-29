package ci.gestion.metier.autreAchatTravaux;

import java.util.List;

import ci.gestion.entites.operation.AutreAchatTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface AutreAchatTravauxMetier extends Imetier<AutreAchatTravaux, Long>{
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdTravaux(long id);

}
