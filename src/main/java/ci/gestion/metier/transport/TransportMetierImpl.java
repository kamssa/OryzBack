package ci.gestion.metier.transport;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.TransportRepository;
import ci.gestion.dao.detail.DetailTransportRepository;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.projet.Projet;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
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
private ProjetRepository projetRepository;

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
		detail.setProjetId(entity.getProjetId());
		}
	entity.setMontant(motantD);
	Transport transport= transportRepository.save(entity);
	Projet projet = projetRepository.findById(transport.getProjetId()).get();
	montantTravaux = projet.getTotal();
	montantT = montantTravaux + transport.getMontant();
	projet.setTotal(montantT);
	Projet pr =projetRepository.save(projet);
		reste = (pr.getDebousserSec())-(pr.getTotal());
		pr.setReste(reste);
		double percent = 100*(pr.getTotal()/pr.getDebousserSec());
		pr.setPercent(percent);
		projetRepository.save(pr);
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
	Projet projet = projetRepository.findById(modif.getProjetId()).get();
    montantTravaux = projet.getTotal();
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
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = (pr.getMontantFacture())-(pr.getTotal());
	       pr.setReste(reste);
	       projetRepository.save(pr);
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
	Projet projet = projetRepository.findById(transport.getProjetId()).get();
	montantTravaux = projet.getTotal();
	montantT = montantTravaux - transport.getMontant();
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = pr.getReste() + transport.getMontant();
	pr.setReste(reste);
	double percent = (pr.getDebousserSec()*pr.getTotal())/100;
	pr.setPercent(percent);
	projetRepository.save(pr);
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
public List<Transport> findTransportByIdProjet(long id) {
	// TODO Auto-generated method stub
	return transportRepository.findTransportByIdProjet(id);
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
	 
     Projet projet = projetRepository.findById(transport.getProjetId()).get();
	 montantTravaux = projet.getTotal();
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
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = (pr.getMontantFacture())-(pr.getTotal());
	       pr.setReste(reste);
	       projetRepository.save(pr);
	       Transport transport2= transportRepository.findById(transport1.getId()).get();
			  montant1 = transport2.getMontant();
			  if (montant1 == 0) {
				transportRepository.deleteById(transport2.getId());
			}
			 return true;
}

@Override
public List<DetailTransport> findDetailTransportByIdProjet(long id) {
	// TODO Auto-generated method stub
	return detailTransportRepository.findDetailTransportByIdProjet(id);
}

@Override
public Double findDetailTransportMontantByIdProjet(long id) {
	double somme = 0d;
	List<DetailTransport> detailTransports = detailTransportRepository.findDetailTransportMontantByIdProjet(id);
	for (DetailTransport detailTransport : detailTransports) {
		somme += detailTransport.getMontant();
	}
	System.out.println("voir la somme"+ somme);
	// TODO Auto-generated method stub
	return somme;
}

@Override
public List<DetailTransport> getDetailTransportBydate(long id, LocalDate dateDebut, LocalDate dateFin) {
	List<DetailTransport> detailAutreAchatTravaux = null;
	  
	List<DetailTransport> detailTransports = detailTransportRepository.findDetailTransportByDateBetweenAndProjetId(dateDebut,dateFin,id);
	  
	  
	  return detailTransports;
}
	
}
