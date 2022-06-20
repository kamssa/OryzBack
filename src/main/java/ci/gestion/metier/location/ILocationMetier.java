package ci.gestion.metier.location;


import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface ILocationMetier extends Imetier<LocationTravaux, Long>{
	List<LocationTravaux> findLocationByIdTravaux(long id);
    boolean supprimerDetailLocation(Long idLocation, Long idDetail);
	List<DetailLocation> findDetailLocationByIdTravaux(long id);
	Double findDetailLocationMontantByIdTravaux(long id);
    List<DetailLocation> getDetailLocationBydate(long idTravaux,LocalDate dateDebut, LocalDate dateFin);

}
