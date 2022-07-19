package ci.gestion.metier.autres;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.metier.utilitaire.Imetier;

public interface IAutresMetier extends Imetier<Autres, Long>{
	List<Autres> findAutresByIdProjet(long id);
    boolean supprimerDetailAutre(Long idAutre, Long idDetail);
	List<DetailAutres> findDetailAutresByIdProjet(long id);
	Double findDetailAutresMontantByIdProjet(long id);
    List<DetailAutres> getDetailAutreBydate(long projetId,LocalDate dateDebut, LocalDate dateFin);


}
