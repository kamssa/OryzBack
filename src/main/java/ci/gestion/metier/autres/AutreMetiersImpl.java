package ci.gestion.metier.autres;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailAutreRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.Autres;
import ci.gestion.entites.autres.DetailAutres; 
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class AutreMetiersImpl implements IAutresMetier{
@Autowired
private AutreRepository autreRepository;
@Autowired
private DetailAutreRepository detailAutreRepository;
@Autowired
private TravauxRepository travauxRepository;

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
		detail.setTravauxId(entity.getTravauxId());

		}
	entity.setMontant(montantD);
	Autres autre= autreRepository.save(entity);
	Travaux travaux = travauxRepository.findById(autre.getTravauxId()).get();
    montantTravaux = travaux.getTotal();
	montantT = montantTravaux + autre.getMontant();
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getDebousserSec())-(tr.getTotal());
		tr.setReste(reste);
		double percent = 100*(tr.getTotal()/tr.getDebousserSec());
		tr.setPercent(percent);
		travauxRepository.save(tr);
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
	Travaux travaux = travauxRepository.findById(modif.getTravauxId()).get();
    montantTravaux = travaux.getTotal();
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
	travaux.setTotal(montantT);
	Travaux tr = travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
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
	Travaux travaux = travauxRepository.findById(autres.getTravauxId()).get();
	montantTravaux = travaux.getTotal();
	montantT = montantTravaux - autres.getMontant();
	travaux.setTotal(montantT);
	Travaux tr = travauxRepository.save(travaux);
	reste = tr.getReste() + autres.getMontant();
	tr.setReste(reste);
	double percent = (tr.getDebousserSec()*tr.getTotal())/100;
	tr.setPercent(percent);
	travauxRepository.save(tr);
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
public List<Autres> findAutresByIdTravaux(long id) {
	// TODO Auto-generated method stub
	return autreRepository.findAutresByIdTravaux(id);
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
	 
     Travaux travaux = travauxRepository.findById(autre.getTravauxId()).get();
	 montantTravaux = travaux.getTotal();
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
	travaux.setTotal(montantT);
	Travaux tr =travauxRepository.save(travaux);
	reste = (tr.getBudget())-(tr.getTotal());
	       tr.setReste(reste);
	       travauxRepository.save(tr);
	       Autres autre2= autreRepository.findById(autre1.getId()).get();
			  montant1 = autre2.getMontant();
			  if (montant1 == 0) {
				autreRepository.deleteById(autre2.getId());
			}
			 return true;
}

@Override
public List<DetailAutres> findDetailAutresByIdTravaux(long id) {
	// TODO Auto-generated method stub
	return detailAutreRepository.findDetailAutresByIdTravaux(id);
}

@Override
public Double findDetailAutresMontantByIdTravaux(long id) {
	double somme = 0d;
	List<DetailAutres> detailAutres = detailAutreRepository.findDetailAutresMontantByIdTravaux(id);
	for (DetailAutres detailAutre : detailAutres) {
		somme += detailAutre.getMontant();
	}
	System.out.println("voir la somme5=====>"+ somme);
	// TODO Auto-generated method stub
	return somme;
}
}


	


