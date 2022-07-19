package ci.gestion.metier.loyer;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.metier.utilitaire.Imetier;

public interface ILoyerMetier  extends Imetier<Loyer, Long>{
	List<Loyer> findLoyerByIdProjet(long id);
    boolean supprimerDetailLoyer(Long idLoyer, Long idDetail);
	List<DetailLoyer> findDetailLoyerByIdProjet(long id);
	Double findDetailLoyerMontantByIdProjet(long id);
    List<DetailLoyer> getDetailLoyerBydate(long idProjet,LocalDate dateDebut, LocalDate dateFin);


}
