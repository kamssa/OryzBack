package ci.gestion.metier.loyer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.LoyerRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailLoyerRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.location.DetailLocation;
import ci.gestion.entites.location.LocationTravaux;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class LoyerMetierImpl implements ILoyerMetier {
@Autowired 
LoyerRepository loyerRepository;
@Autowired 
DetailLoyerRepository detailLoyerRepository;
@Autowired
TravauxRepository travauxRepository;
	@Override
	public Loyer creer(Loyer entity) throws InvalideOryzException {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		List<DetailLoyer> detailLoyers = entity.getDetailLoyer();
		for(DetailLoyer detail : detailLoyers) {
			motantD = detail.getMontant();
			detail.setMontant(motantD);
			detail.setTravauxId(entity.getTravauxId());
			}
		entity.setMontant(motantD);
		Loyer loyer= loyerRepository.save(entity);
		Travaux travaux = travauxRepository.findById(loyer.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montantT = montantTravaux + loyer.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
			reste = (tr.getDebousserSec())-(tr.getTotal());
			tr.setReste(reste);
			double percent = 100*(tr.getTotal()/tr.getDebousserSec());
			tr.setPercent(percent);
			travauxRepository.save(tr);
		 return loyer;
		 
	}

	@Override
	public Loyer modifier(Loyer modif) throws InvalideOryzException {
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
		List<DetailLoyer> detailLoyers = modif.getDetailLoyer();
		for(DetailLoyer detail : detailLoyers) {
			motantD = detail.getMontant();
			detail.setMontant(motantD);
			sommeMontant += motantD;
			}
		modif.setMontant(sommeMontant);
		
	         
	    Loyer loyer= loyerRepository.save(modif);
	    montantT = montantModifie + loyer.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return loyer;
	}

	@Override
	public List<Loyer> findAll() {
		// TODO Auto-generated method stub
		return loyerRepository.findAll();
	}

	@Override
	public Loyer findById(Long id) {
		// TODO Auto-generated method stub
		return loyerRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		Loyer loyer = findById(id);
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		Travaux travaux = travauxRepository.findById(loyer.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montantT = montantTravaux - loyer.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = tr.getReste() + loyer.getMontant();
		tr.setReste(reste);
		double percent = (tr.getDebousserSec()*tr.getTotal())/100;
		tr.setPercent(percent);
		travauxRepository.save(tr);
				loyerRepository.deleteById(id);
                return true; 
	}

	@Override
	public boolean supprimer(List<Loyer> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Loyer> findLoyerByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return loyerRepository.findLoyerByIdTravaux(id);
	}

	@Override
	public boolean supprimerDetailLoyer(Long idLoyer, Long idDetail) {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double montantLoyer = 0;
		double montant = 0;
		double reste=0;
		double montant1 = 0;
		 detailLoyerRepository.deleteById(idDetail);
		 Loyer loyer = findById(idLoyer);
		 montantLoyer = loyer.getMontant();
		 
	     Travaux travaux = travauxRepository.findById(loyer.getTravauxId()).get();
		 montantTravaux = travaux.getTotal();
		 montant = montantTravaux - montantLoyer;
		
	   	List<DetailLoyer> detailLoyers = loyer.getDetailLoyer();
		 
	     for(DetailLoyer detail : detailLoyers) {
			motantD = detail.getMontant();
			detail.setMontant(motantD);
			sommeMontant += motantD;
			System.out.println("Voir detail" + detail);
			}
		loyer.setMontant(sommeMontant);
		Loyer loyer1= loyerRepository.save(loyer);
	   
		montantT = montant + loyer1.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		       Loyer loyer2= loyerRepository.findById(loyer1.getId()).get();
				  montant1 = loyer2.getMontant();
				  if (montant1 == 0) {
					loyerRepository.deleteById(loyer2.getId());
				}
				 return true;
	}

	@Override
	public List<DetailLoyer> findDetailLoyerByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return detailLoyerRepository.findDetailLoyerByIdTravaux(id);
	}

	@Override
	public Double findDetailLoyerMontantByIdTravaux(long id) {
		double somme = 0d;
		List<DetailLoyer> detailLoyers = detailLoyerRepository.findDetailLoyerMontantByIdTravaux(id);
		for (DetailLoyer detailLoyer : detailLoyers) {
			somme += detailLoyer.getMontant();
		}
		System.out.println("voir la somme LOYER&&&&&&&&&&&&&&&&&&&"+ somme);
		// TODO Auto-generated method stub
		return somme;
	}

	@Override
	public List<DetailLoyer> getDetailLoyerBydate(long id, LocalDate dateDebut, LocalDate dateFin) {
		List<DetailLoyer> detailAutreAchatTravaux = null;
		  
		List<DetailLoyer> detailLoyers = detailLoyerRepository.findDetailLoyerByDateBetweenAndTravauxId(dateDebut,dateFin, id);
		  
		  
		  return detailLoyers;
	}

}
