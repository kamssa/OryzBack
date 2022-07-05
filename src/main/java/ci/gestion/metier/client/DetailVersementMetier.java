package ci.gestion.metier.client;


import java.util.List;

import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.metier.utilitaire.Imetier;


public interface DetailVersementMetier extends Imetier<DetailVersement, Long>{
	List<DetailVersement> detailVersementByIdVersement(Long id);
	List<DetailVersement> detailVersementByIdPersonne(Long id);
	List<DetailVersement> findDetailVersementByIdTravaux(long id);


	
}
