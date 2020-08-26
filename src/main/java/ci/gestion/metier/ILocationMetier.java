package ci.gestion.metier;


import java.util.List;

import ci.gestion.entites.location.LocationTravaux;

public interface ILocationMetier extends Imetier<LocationTravaux, Long>{
	List<LocationTravaux> findLocationByIdTravaux(long id);
    boolean supprimerDetailLocation(Long idLocation, Long idDetail);

}
