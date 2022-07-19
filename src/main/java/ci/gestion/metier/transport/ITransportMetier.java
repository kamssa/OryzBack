package ci.gestion.metier.transport;

import java.time.LocalDate;
import java.util.List;

import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.transport.DetailTransport;
import ci.gestion.entites.transport.Transport;
import ci.gestion.metier.utilitaire.Imetier;

public interface ITransportMetier extends Imetier<Transport, Long>{
	List<Transport> findTransportByIdProjet(long id);
    boolean supprimerDetailTransport(Long idTransport, Long idDetail);
	List<DetailTransport> findDetailTransportByIdProjet(long id);
	Double findDetailTransportMontantByIdProjet(long id);
    List<DetailTransport> getDetailTransportBydate(long idProjet,LocalDate dateDebut, LocalDate dateFin);



}
