package ci.gestion.metier.mainOeuvre;

import java.util.List;

import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.metier.utilitaire.Imetier;

public interface IMainDoeuvreMetier extends Imetier<MainOeuvre, Long>{
	List<MainOeuvre> findMainOeuvreByIdTravaux(long id);
    boolean supprimerDetailMainOeuvre(Long idMainOeuvre, Long idDetail);

}
