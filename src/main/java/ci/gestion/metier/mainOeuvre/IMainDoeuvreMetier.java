package ci.gestion.metier.mainOeuvre;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.metier.utilitaire.Imetier;

public interface IMainDoeuvreMetier extends Imetier<MainOeuvre, Long>{
	List<MainOeuvre> findMainOeuvreByIdTravaux(long id);
    boolean supprimerDetailMainOeuvre(Long idMainOeuvre, Long idDetail);
    List<DetailMainOeuvre> findDetailMainOeuvreByIdTravaux(long id);
	Double findDetailMainOeuvreMontantByIdTravaux(long id);
    List<DetailMainOeuvre> getDetailMainBydate(long idTravaux,LocalDate dateDebut, LocalDate dateFin);


}
