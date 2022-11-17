package ci.gestion.metier.vehicule;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.vehicule.Prestation;
import ci.gestion.metier.utilitaire.Imetier;

public interface CarburantMetier extends Imetier<Prestation, Long>{
	List<Prestation> getCarburantByEntreprise(long id);
		List<Prestation> getCarburantVehiculeParDate(long vehiculeId,LocalDate startDate, LocalDate endDate);


}
