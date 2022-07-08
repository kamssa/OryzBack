package ci.gestion.metier.versement;


import java.util.List;

import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.metier.utilitaire.Imetier;


public interface DetailVersementMetier extends Imetier<DetailVersement, Long>{
	List<DetailVersement> findDetailVersementByidVersement(long id);

}
