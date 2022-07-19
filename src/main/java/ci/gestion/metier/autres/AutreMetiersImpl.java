package ci.gestion.metier.autres;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.detail.DetailAutreRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.site.Projet;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class AutreMetiersImpl implements IAutresMetier{
@Autowired
private AutreRepository autreRepository;
@Autowired
private DetailAutreRepository detailAutreRepository;
@Autowired
private ProjetRepository projetRepository;

@Override
public Autres creer(Autres entity) throws InvalideOryzException {
	double montantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	List<DetailAutres> detailAutres = entity.getDetailAutres();
	for(DetailAutres detail : detailAutres) {
		montantD = ((detail.getPrixUnitaire() * detail.getQuantite()));
		detail.setMontant(montantD);
		detail.setProjetId(entity.getProjetId());

		}
	entity.setMontant(montantD);
	Autres autre= autreRepository.save(entity);
	Projet projet = projetRepository.findById(autre.getProjetId()).get();
    montantTravaux = projet.getTotal();
	montantT = montantTravaux + autre.getMontant();
	projet.setTotal(montantT);
	Projet pr =projetRepository.save(projet);
		reste = (pr.getDebousserSec())-(pr.getTotal());
		pr.setReste(reste);
		double percent = 100*(pr.getTotal()/pr.getDebousserSec());
		pr.setPercent(percent);
		projetRepository.save(pr);
	 return autre;
}

@Override
public Autres modifier(Autres modif) throws InvalideOryzException {
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
	List<DetailAutres> detailAutres = modif.getDetailAutres();
	for(DetailAutres detail : detailAutres) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		sommeMontant += motantD;
		}
	modif.setMontant(sommeMontant);
	
         
    Autres autres= autreRepository.save(modif);
    montantT = montantModifie + autres.getMontant();
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = (pr.getBudget())-(pr.getTotal());
	       pr.setReste(reste);
	       projetRepository.save(pr);
	 return autres;
}

@Override
public List<Autres> findAll() {
	// TODO Auto-generated method stub
	return autreRepository.findAll();
}

@Override
public Autres findById(Long id) {
	// TODO Auto-generated method stub
	return autreRepository.findById(id).get();
}

@Override
public boolean supprimer(Long id) {
	Autres autres = findById(id);
	double montantTravaux = 0;
	double montantT = 0;
	double reste=0;
	Projet projet = projetRepository.findById(autres.getProjetId()).get();
	montantTravaux = projet.getTotal();
	montantT = montantTravaux - autres.getMontant();
	projet.setTotal(montantT);
	Projet pr = projetRepository.save(projet);
	reste = pr.getReste() + autres.getMontant();
	pr.setReste(reste);
	double percent = (pr.getDebousserSec()*pr.getTotal())/100;
	pr.setPercent(percent);
	projetRepository.save(pr);
	autreRepository.deleteById(id);
            return true; 
}

@Override
public boolean supprimer(List<Autres> entites) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean existe(Long id) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public List<Autres> findAutresByIdProjet(long id) {
	// TODO Auto-generated method stub
	return autreRepository.findAutresByIdProjet(id);
}

@Override
public boolean supprimerDetailAutre(Long idAutre, Long idDetail) {
	double motantD = 0;
	double montantTravaux = 0;
	double montantT = 0;
	double sommeMontant = 0;
	double montantAutre = 0;
	double montant = 0;
	double reste=0;
	double montant1 = 0;
	 detailAutreRepository.deleteById(idDetail);
	 Autres autre = findById(idAutre);
	 montantAutre = autre.getMontant();
	 
     Projet projet = projetRepository.findById(autre.getProjetId()).get();
	 montantTravaux = projet.getTotal();
	 montant = montantTravaux - montantAutre;
	
   	List<DetailAutres> detailAutres = autre.getDetailAutres();
	 
     for(DetailAutres detail : detailAutres) {
		motantD = detail.getMontant();
		detail.setMontant(motantD);
		sommeMontant += motantD;
		System.out.println("Voir detail" + detail);
		}
	autre.setMontant(sommeMontant);
	Autres autre1= autreRepository.save(autre);
   
	montantT = montant + autre1.getMontant();
	projet.setTotal(montantT);
	Projet pr =projetRepository.save(projet);
	reste = (pr.getBudget())-(pr.getTotal());
	       pr.setReste(reste);
	       projetRepository.save(pr);
	       Autres autre2= autreRepository.findById(autre1.getId()).get();
			  montant1 = autre2.getMontant();
			  if (montant1 == 0) {
				autreRepository.deleteById(autre2.getId());
			}
			 return true;
}

@Override
public List<DetailAutres> findDetailAutresByIdProjet(long id) {
	// TODO Auto-generated method stub
	return detailAutreRepository.findDetailAutresByIdTravaux(id);
}

@Override
public Double findDetailAutresMontantByIdProjet(long id) {
	double somme = 0d;
	List<DetailAutres> detailAutres = detailAutreRepository.findDetailAutresMontantByIdProjet(id);
	for (DetailAutres detailAutre : detailAutres) {
		somme += detailAutre.getMontant();
	}
	System.out.println("voir la somme5=====>"+ somme);
	// TODO Auto-generated method stub
	return somme;
}

@Override
public List<DetailAutres> getDetailAutreBydate(long idTravaux, LocalDate dateDebut, LocalDate dateFin) {
	  
	List<DetailAutres> detailAutres = detailAutreRepository.findDetailAutresByDateBetweenAndProjetId(dateDebut, dateFin,idTravaux);
	    
	  
	  return detailAutres;
}
}


	


