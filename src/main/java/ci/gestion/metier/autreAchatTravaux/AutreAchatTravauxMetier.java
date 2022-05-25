package ci.gestion.metier.autreAchatTravaux;

import java.util.List;

import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface AutreAchatTravauxMetier extends Imetier<AutreAchatTravaux, Long>{
	List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdTravaux(long id);
	List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdTravaux(long id);
	Double findDetailAutreAchatTravauxMontantByIdTravaux(long id);

}
