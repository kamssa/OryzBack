package ci.gestion.metier.combo;

import java.util.List;


import ci.gestion.entites.combo.Materiel;
import ci.gestion.metier.Imetier;

public interface MaterielMetier extends Imetier<Materiel, Long>{
	List<Materiel>  getMaterielByIdCategorie(long id);
}
