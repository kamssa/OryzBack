package ci.gestion.metier.location;


import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface ILocationMetier extends Imetier<LocationTravaux, Long>{
	List<LocationTravaux> findLocationByIdProjet(long id);
    boolean supprimerDetailLocation(Long idLocation, Long idDetail);
	List<DetailLocation> findDetailLocationByIdProjet(long id);
	Double findDetailLocationMontantByIdProjet(long id);
    List<DetailLocation> getDetailLocationBydate(long idProjet,LocalDate dateDebut, LocalDate dateFin);

}
