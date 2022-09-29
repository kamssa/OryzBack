 package ci.gestion.metier.banque;

import java.util.List;

import ci.gestion.entites.banque.Banque;
import ci.gestion.metier.utilitaire.Imetier;

public interface IBanqueMetier extends Imetier<Banque, Long>{
	List<Banque> getBanqueByIdEntreprise(long id);

}
