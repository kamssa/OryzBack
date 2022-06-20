package ci.gestion.metier.autres;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.metier.utilitaire.Imetier;

public interface IAutresMetier extends Imetier<Autres, Long>{
	List<Autres> findAutresByIdTravaux(long id);
    boolean supprimerDetailAutre(Long idAutre, Long idDetail);
	List<DetailAutres> findDetailAutresByIdTravaux(long id);
	Double findDetailAutresMontantByIdTravaux(long id);
    List<DetailAutres> getDetailAutreBydate(long idTravaux,LocalDate dateDebut, LocalDate dateFin);


}
