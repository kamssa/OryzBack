package ci.gestion.metier.vehicule;

import java.util.List;

import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.utilitaire.Imetier;

public interface VehiculeMetier extends Imetier<Vehicule, Long>{
	List<Vehicule> getVehiculeByIdEntreprise(long id);
}
