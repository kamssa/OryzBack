package ci.gestion.metier.combo;

import java.util.List;

import ci.gestion.entites.article.Materiaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface MateriauxMetier extends Imetier<Materiaux, Long>{
	List<Materiaux>  getMaterielByIdCategorie(long id);
}
