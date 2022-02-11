package ci.gestion.metier.combo;

import java.util.List;


import ci.gestion.entites.operation.Materiaux;
import ci.gestion.metier.Imetier;

public interface MateriauxMetier extends Imetier<Materiaux, Long>{
	List<Materiaux>  getMaterielByIdCategorie(long id);
}
