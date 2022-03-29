package ci.gestion.metier.location;


import java.util.List;

import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface ILocationMetier extends Imetier<LocationTravaux, Long>{
	List<LocationTravaux> findLocationByIdTravaux(long id);
    boolean supprimerDetailLocation(Long idLocation, Long idDetail);

}
