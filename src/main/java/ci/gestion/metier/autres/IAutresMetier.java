package ci.gestion.metier.autres;

import java.util.List;

import ci.gestion.entites.autres.Autres;
import ci.gestion.metier.Imetier;

public interface IAutresMetier extends Imetier<Autres, Long>{
	List<Autres> findAutresByIdTravaux(long id);
    boolean supprimerDetailAutre(Long idAutre, Long idDetail);

}
