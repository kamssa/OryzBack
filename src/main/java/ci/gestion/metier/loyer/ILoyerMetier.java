package ci.gestion.metier.loyer;

import java.util.List;

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.metier.utilitaire.Imetier;

public interface ILoyerMetier  extends Imetier<Loyer, Long>{
	List<Loyer> findLoyerByIdTravaux(long id);
    boolean supprimerDetailLoyer(Long idLoyer, Long idDetail);
	List<DetailLoyer> findDetailLoyerByIdTravaux(long id);
	

}
