package ci.gestion.metier.vehicule;

import java.util.List;

import ci.gestion.entites.vehicule.StationEssence;
import ci.gestion.metier.utilitaire.Imetier;

public interface StationEssenceMetier extends Imetier<StationEssence, Long>{
	List<StationEssence> getStationEssenceByIdEntreprise(long id);

}
