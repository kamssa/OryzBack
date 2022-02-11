package ci.gestion.metier.combo;

import java.util.List;
import java.util.Optional;

import ci.gestion.entites.operation.Categorie;
import ci.gestion.metier.Imetier;

public interface CategorieMetier extends Imetier<Categorie, Long>{
	List<Categorie> getCategorieByIdEntreprise(long id);
	Optional<Categorie> findByLibelle(String libelle);
}
