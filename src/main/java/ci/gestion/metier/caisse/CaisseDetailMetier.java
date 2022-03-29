package ci.gestion.metier.caisse;

import java.util.List;

import ci.gestion.entites.caisse.CaisseDetail;
import ci.gestion.metier.utilitaire.Imetier;

public interface CaisseDetailMetier extends Imetier<CaisseDetail, Long>{
	List<CaisseDetail> findCaisseDetailByIdEntreprise(long id);

}
