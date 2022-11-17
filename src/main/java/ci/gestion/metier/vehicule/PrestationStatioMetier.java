package ci.gestion.metier.vehicule;

import ci.gestion.entites.vehicule.PrestationStation;
import ci.gestion.metier.utilitaire.Imetier;

public interface PrestationStatioMetier extends Imetier<PrestationStation, Long>{
	PrestationStation findByLibelle(String libelle);

}
