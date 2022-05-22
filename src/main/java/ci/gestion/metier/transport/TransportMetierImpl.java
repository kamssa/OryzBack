package ci.gestion.metier.transport;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ci.gestion.dao.TransportRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailTransportRepository;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.entites.transport.DetailTransport;
import ci.gestion.entites.transport.Transport;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class TransportMetierImpl implements ITransportMetier{
@Autowired
private TransportRepository transportRepository;
@Autowired
private DetailTransportRepository detailTransportRepository;
@Autowired
private TravauxRepository travauxRepository;

@Override
public Transport creer(Transport entity) throws InvalideOryzException {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	List<DetailTransport> detailTransports = entity.getDetailTransport();
	for(DetailTransport detail : detailTransports) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		detail.setTravauxId(entity.getTravauxId());
		}
	entity.setMontant(motantD);
	Transport transport= transportRepository.save(entity);
	Travaux travaux = travauxRepository.findById(transport.getTravauxId()).get();
	montantTravaux = travaux.getTotal();
	montantT = montantTravaux + transport.getMontant();
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getDebousserSec())-(tr.getTotal());
		tr.setReste(reste);
		double percent = 100*(tr.getTotal()/tr.getDebousserSec());
		tr.setPercent(percent);
		travauxRepository.save(tr);
	 return transport;
}

@Override
public Transport modifier(Transport modif) throws InvalideOryzException {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double sommeMontant = 0;
	double reste=0;
	double montantModifie = 0;
	Travaux travaux = travauxRepository.findById(modif.getTravauxId()).get();
    montantTravaux = travaux.getTotal();
    montantModifie = montantTravaux - modif.getMontant();
    System.out.println(" le montant total  reduit"+montantModifie);
	List<DetailTransport> detailTransports = modif.getDetailTransport();
	for(DetailTransport detail : detailTransports) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		sommeMontant += motantD;
		}
	modif.setMontant(sommeMontant);
	Transport transport= transportRepository.save(modif);
    montantT = montantModifie + transport.getMontant();
	travaux.setTotal(montantT);
	Travaux tr = travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
	 return transport;
}

@Override
public List<Transport> findAll() {
	
	return transportRepository.findAll();
}

@Override
public Transport findById(Long id) {
	
	return transportRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	Transport transport = findById(id);
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	Travaux travaux = travauxRepository.findById(transport.getTravauxId()).get();
	montantTravaux = travaux.getTotal();
	montantT = montantTravaux - transport.getMontant();
	travaux.setTotal(montantT);
	Travaux tr = travauxRepository.save(travaux);
	reste = tr.getReste() + transport.getMontant();
	tr.setReste(reste);
	double percent = (tr.getDebousserSec()*tr.getTotal())/100;
	tr.setPercent(percent);
	travauxRepository.save(tr);
			transportRepository.deleteById(id);
            return true; 
}

@Override
public boolean supprimer(List<Transport> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<Transport> findTransportByIdTravaux(long id) {
	// TODO Auto-generated method stub
	return transportRepository.findTransportByIdTravaux(id);
}

@Override
public boolean supprimerDetailTransport(Long idTransport, Long idDetail) {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double sommeMontant = 0;
	double montantTransport = 0;
	double montant = 0;
	double reste=0;
	double montant1 = 0;
	 detailTransportRepository.deleteById(idDetail);
	 Transport transport = findById(idTransport);
	 montantTransport = transport.getMontant();
	 
     Travaux travaux = travauxRepository.findById(transport.getTravauxId()).get();
	 montantTravaux = travaux.getTotal();
	 montant = montantTravaux - montantTransport;
	
   	List<DetailTransport> detailTransports = transport.getDetailTransport();
	 
     for(DetailTransport detail : detailTransports) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		sommeMontant += motantD;
		System.out.println("Voir detail" + detail);
		}
	transport.setMontant(sommeMontant);
	Transport transport1= transportRepository.save(transport);
   
	montantT = montant + transport1.getMontant();
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
	       Transport transport2= transportRepository.findById(transport1.getId()).get();
			  montant1 = transport2.getMontant();
			  if (montant1 == 0) {
				transportRepository.deleteById(transport2.getId());
			}
			 return true;
}

@Override
public List<DetailTransport> findDetailTransportByIdTravaux(long id) {
	// TODO Auto-generated method stub
	return detailTransportRepository.findDetailTransportByIdTravaux(id);
}
	
}
