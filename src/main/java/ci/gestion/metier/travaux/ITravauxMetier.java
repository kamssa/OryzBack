package ci.gestion.metier.travaux;

import java.util.List;

import org.springframework.data.repository.query.Param;

import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.utilitaire.Imetier;

public interface ITravauxMetier extends Imetier<Travaux, Long>{
	List<Travaux> chercherTravauxParMc(String mc, String nom);
	List<Travaux> findTravauxByIdSite(long id);
	List<Travaux> findTravauxByIdClient(long id);



}
