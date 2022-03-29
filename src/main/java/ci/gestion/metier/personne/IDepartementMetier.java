package ci.gestion.metier.personne;

import java.util.List;

import ci.gestion.entites.entreprise.Departement;
import ci.gestion.metier.utilitaire.Imetier;


public interface IDepartementMetier extends Imetier<Departement, Long>{
	List<Departement> getDepByIdEntreprise(Long id);
	public Departement findDepartementByLibelle(String libelle);
}
