package ci.gestion.metier.loyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.LoyerRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailLoyerRepository;
import ci.gestion.entites.Travaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
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
			motantD += detail.getMontant();
			detail.setMontant(motantD);
			}
		entity.setMontant(motantD);
		Loyer loyer= loyerRepository.save(entity);
		Travaux travaux = travauxRepository.findById(loyer.getTravauxId()).get();
        montantTravaux = travaux.getTotal();
		montantT = montantTravaux + loyer.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
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
		reste = tr.getReste()+ loyer.getMontant();
		       tr.setReste(reste);
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
		 return true;
	}

}
