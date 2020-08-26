package ci.gestion.metier.transport;

import java.util.List;

import ci.gestion.entites.transport.Transport;
import ci.gestion.metier.Imetier;

public interface ITransportMetier extends Imetier<Transport, Long>{
	List<Transport> findTransportByIdTravaux(long id);
    boolean supprimerDetailTransport(Long idTransport, Long idDetail);

}
