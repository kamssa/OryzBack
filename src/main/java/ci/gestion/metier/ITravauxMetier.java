package ci.gestion.metier;

import java.util.List;

import org.springframework.data.repository.query.Param;

import ci.gestion.entites.site.Travaux;

public interface ITravauxMetier extends Imetier<Travaux, Long>{
	List<Travaux> chercherTravauxParMc(String mc);
	List<Travaux> findTravauxByIdSite(long id);


}
