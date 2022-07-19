package ci.gestion.metier.loyer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.LoyerRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.detail.DetailLoyerRepository;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.site.Projet;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class LoyerMetierImpl implements ILoyerMetier {
@Autowired 
LoyerRepository loyerRepository;
@Autowired 
DetailLoyerRepository detailLoyerRepository;
@Autowired
ProjetRepository projetRepository;
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
			detail.setProjetId(entity.getProjetId());
			}
		entity.setMontant(motantD);
		Loyer loyer= loyerRepository.save(entity);
		Projet projet = projetRepository.findById(loyer.getProjetId()).get();
		montantTravaux = projet.getTotal();
		montantT = montantTravaux + loyer.getMontant();
		projet.setTotal(montantT);
		Projet pr =projetRepository.save(projet);
			reste = (pr.getDebousserSec())-(pr.getTotal());
			pr.setReste(reste);
			double percent = 100*(pr.getTotal()/pr.getDebousserSec());
			pr.setPercent(percent);
			projetRepository.save(pr);
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
		Projet projet = projetRepository.findById(modif.getProjetId()).get();
	    montantTravaux = projet.getTotal();
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
		projet.setTotal(montantT);
		Projet pr = projetRepository.save(projet);
		reste = (pr.getBudget())-(pr.getTotal());
		       pr.setReste(reste);
		       projetRepository.save(pr);
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
		Projet projet = projetRepository.findById(loyer.getProjetId()).get();
		montantTravaux = projet.getTotal();
		montantT = montantTravaux - loyer.getMontant();
		projet.setTotal(montantT);
		Projet pr = projetRepository.save(projet);
		reste = pr.getReste() + loyer.getMontant();
		pr.setReste(reste);
		double percent = (pr.getDebousserSec()*pr.getTotal())/100;
		pr.setPercent(percent);
		projetRepository.save(pr);
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
	public List<Loyer> findLoyerByIdProjet(long id) {
		// TODO Auto-generated method stub
		return loyerRepository.findLoyerByIdProjet(id);
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
		 
	     Projet projet = projetRepository.findById(loyer.getProjetId()).get();
		 montantTravaux = projet.getTotal();
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
		projet.setTotal(montantT);
		Projet pr =projetRepository.save(projet);
		reste = (pr.getBudget())-(pr.getTotal());
		       pr.setReste(reste);
		       projetRepository.save(pr);
		       Loyer loyer2= loyerRepository.findById(loyer1.getId()).get();
				  montant1 = loyer2.getMontant();
				  if (montant1 == 0) {
					loyerRepository.deleteById(loyer2.getId());
				}
				 return true;
	}

	@Override
	public List<DetailLoyer> findDetailLoyerByIdProjet(long id) {
		// TODO Auto-generated method stub
		return detailLoyerRepository.findDetailLoyerByIdProjet(id);
	}

	@Override
	public Double findDetailLoyerMontantByIdProjet(long id) {
		double somme = 0d;
		List<DetailLoyer> detailLoyers = detailLoyerRepository.findDetailLoyerMontantByIdProjet(id);
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
		  
		List<DetailLoyer> detailLoyers = detailLoyerRepository.findDetailLoyerByDateBetweenAndProjetId(dateDebut,dateFin, id);
		  
		  
		  return detailLoyers;
	}

}
