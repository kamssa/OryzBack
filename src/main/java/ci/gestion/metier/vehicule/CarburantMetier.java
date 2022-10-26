package ci.gestion.metier.vehicule;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.metier.utilitaire.Imetier;

public interface CarburantMetier extends Imetier<Carburant, Long>{
	List<Carburant> getCarburantByEntreprise(long id);
		List<Carburant> getCarburantVehiculeParDate(long vehiculeId,LocalDate startDate, LocalDate endDate);


}
