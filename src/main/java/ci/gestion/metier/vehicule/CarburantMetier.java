package ci.gestion.metier.vehicule;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.Vehicule;
import ci.gestion.metier.utilitaire.Imetier;

public interface CarburantMetier extends Imetier<Carburant, Long>{
	  List<Carburant>findCarburantByDateBetweenAndEntreprise(Vehicule vehicule, LocalDate dateDebut, LocalDate dateFin);
}