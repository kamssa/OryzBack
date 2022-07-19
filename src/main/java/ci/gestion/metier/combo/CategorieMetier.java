package ci.gestion.metier.combo;

import java.util.List;
import java.util.Optional;

import ci.gestion.entites.stock.Categorie;
import ci.gestion.entites.stock.Materiaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface CategorieMetier extends Imetier<Categorie, Long>{
	List<Categorie> getCategorieByIdEntreprise(long id);
	Optional<Categorie> findByLibelle(String libelle);
	List<Materiaux> listMatParEntreprise(long id);
}
