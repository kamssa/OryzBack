package ci.gestion.metier.combo;

import java.util.List;

import ci.gestion.entites.combo.Categorie;
import ci.gestion.metier.Imetier;

public interface CategorieMetier extends Imetier<Categorie, Long>{
	List<Categorie> getCategorieByIdEntreprise(long id);
}
