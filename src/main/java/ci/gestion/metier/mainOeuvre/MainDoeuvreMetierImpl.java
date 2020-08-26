package ci.gestion.metier.mainOeuvre;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.MainDoeuvreRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailMainOeuvreRepository;
import ci.gestion.entites.Travaux;
import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.loyer.Loyer;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.entites.transport.DetailTransport;
import ci.gestion.entites.transport.Transport;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class MainDoeuvreMetierImpl implements IMainDoeuvreMetier{
@Autowired
private MainDoeuvreRepository mainDoeuvreRepository;
@Autowired
private DetailMainOeuvreRepository detailMainOeuvreRepository;
@Autowired
private TravauxRepository travauxRepository;
	@Override
	public MainOeuvre creer(MainOeuvre entity) throws InvalideOryzException {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		List<DetailMainOeuvre> detailMainOeuvres = entity.getDetailMainOeuvre();
		for(DetailMainOeuvre detail : detailMainOeuvres) {
			motantD += detail.getMontantVerser();
			detail.setMontantVerser(motantD);
			}
		entity.setMontant(motantD);
		MainOeuvre mainOeuvre= mainDoeuvreRepository.save(entity);
		Travaux travaux = travauxRepository.findById(mainOeuvre.getTravauxId()).get();
        montantTravaux = travaux.getTotal();
		montantT = montantTravaux + mainOeuvre.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return mainOeuvre;
	}

	@Override
	public MainOeuvre modifier(MainOeuvre modif) throws InvalideOryzException {
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
		List<DetailMainOeuvre> detailMainOeuvres = modif.getDetailMainOeuvre();
		for(DetailMainOeuvre detail : detailMainOeuvres) {
			motantD = detail.getMontantVerser();
			detail.setMontantVerser(motantD);
			sommeMontant += motantD;
			}
		modif.setMontant(sommeMontant);
		
	         
	    MainOeuvre mainOeuvre= mainDoeuvreRepository.save(modif);
	    montantT = montantModifie + mainOeuvre.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return mainOeuvre;
	}

	@Override
	public List<MainOeuvre> findAll() {
		// TODO Auto-generated method stub
		return mainDoeuvreRepository.findAll();
	}

	@Override
	public MainOeuvre findById(Long id) {
		// TODO Auto-generated method stub
		return mainDoeuvreRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		MainOeuvre mainOuvre = findById(id);
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		Travaux travaux = travauxRepository.findById(mainOuvre.getTravauxId()).get();
        montantTravaux = travaux.getTotal();
		montantT = montantTravaux - mainOuvre.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = tr.getReste()+ mainOuvre.getMontant();
		       tr.setReste(reste);
		       travauxRepository.save(tr);
				mainDoeuvreRepository.deleteById(id);
                return true; 
	}

	@Override
	public boolean supprimer(List<MainOeuvre> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<MainOeuvre> findMainOeuvreByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return mainDoeuvreRepository.findMainOeuvreByIdTravaux(id);
	}

	@Override
	public boolean supprimerDetailMainOeuvre(Long idMainOeuvre, Long idDetail) {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double montantMainOeuvre = 0;
		double montant = 0;
		double reste=0;
		 detailMainOeuvreRepository.deleteById(idDetail);
		 MainOeuvre mainOeuvre = findById(idMainOeuvre);
		 montantMainOeuvre = mainOeuvre.getMontant();
		 
	     Travaux travaux = travauxRepository.findById(mainOeuvre.getTravauxId()).get();
		 montantTravaux = travaux.getTotal();
		 montant = montantTravaux - montantMainOeuvre;
		
	   	List<DetailMainOeuvre> detailMainOeuvres = mainOeuvre.getDetailMainOeuvre();
		 
	     for(DetailMainOeuvre detail : detailMainOeuvres) {
			motantD = detail.getMontantVerser();
			detail.setMontantVerser(motantD);
			sommeMontant += motantD;
			System.out.println("Voir detail" + detail);
			}
		mainOeuvre.setMontant(sommeMontant);
		MainOeuvre mainOeuvre1= mainDoeuvreRepository.save(mainOeuvre);
	   
		montantT = montant + mainOeuvre1.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return true;
	}

}
