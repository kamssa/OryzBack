package ci.gestion.metier;

import java.util.List;

import ci.gestion.entites.operation.AutreAchatTravaux;

public interface AutreAchatTravauxMetier extends Imetier<AutreAchatTravaux, Long>{
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdTravaux(long id);

}
