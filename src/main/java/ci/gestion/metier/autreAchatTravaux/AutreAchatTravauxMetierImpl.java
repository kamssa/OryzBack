package ci.gestion.metier.autreAchatTravaux;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreAchatTravauxRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.detail.DetailAutreAchatTravauxRepository;
import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.projet.Projet;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutreAchatTravauxMetierImpl implements AutreAchatTravauxMetier{
private AutreAchatTravauxRepository autreAchatTravauxRepository;
private DetailAutreAchatTravauxRepository detailAutreAchatTravauxRepository;

private ProjetRepository projetRepository;
	@Override
	public AutreAchatTravaux creer(AutreAchatTravaux entity) throws InvalideOryzException {
		AutreAchatTravaux autreAchat = null;
		double montantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		List<DetailAutreAchatTravaux> detaiAutrelAchats = entity.getDetailAutreAchatTravaux();
		for (DetailAutreAchatTravaux detail : detaiAutrelAchats) {
		      
			montantD = ((detail.getPrixUnitaire() * detail.getQuantite())+ detail.getFrais());
				detail.setMontant(montantD);
				detail.setProjetId(entity.getProjetId());
				sommeMontant = montantD;
				 entity.setTotal(sommeMontant);
				//entity.setLibelle(detail.getLibelleMateriaux());
				//entity.setQuantite(detail.getQuantite());

				autreAchat = autreAchatTravauxRepository.save(entity);
				Projet projet = projetRepository.findById(autreAchat.getProjetId()).get();
				montantTravaux = projet.getTotal();
				montantT = montantTravaux + autreAchat.getMontant();
				projet.setTotal(montantT);
				Projet pr =projetRepository.save(projet);
				reste = (pr.getDebousserSec())-(pr.getTotal());
				pr.setReste(reste);
				double percent = 100*(pr.getTotal()/pr.getDebousserSec());
				pr.setPercent(percent);
				projetRepository.save(pr);
			

		}

		return autreAchat;
	
	}

	@Override
	public AutreAchatTravaux modifier(AutreAchatTravaux entity) throws InvalideOryzException {
		AutreAchatTravaux autreAchat = null;
		AutreAchatTravaux ancienAutreAchat = null;
		double montantD = 0;
		double montantTravaux = 0;
		double ancienMontantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		double ancienMontant = 0;
		List<DetailAutreAchatTravaux> detaiAutrelAchats = entity.getDetailAutreAchatTravaux();
		/*
		 * for (DetailAutreAchatTravaux detail : detaiAutrelAchats) {
		 * 
		 * montantD = ((detail.getPrixUnitaire() * detail.getQuantite())+
		 * detail.getFrais()); detail.setMontant(montantD);
		 * detail.setProjetId(entity.getProjetId()); sommeMontant = montantD;
		 * entity.setTotal(sommeMontant);
		 */				//entity.setLibelle(detail.getLibelleMateriaux());
				//entity.setQuantite(detail.getQuantite());
		        ancienAutreAchat = autreAchatTravauxRepository.findById(entity.getId()).get();
				autreAchat = autreAchatTravauxRepository.save(entity);
				Projet projet = projetRepository.findById(autreAchat.getProjetId()).get();
				montantTravaux = projet.getTotal();
				//ancienMontantTravaux = montantTravaux - ancienAutreAchat.getMontant();
				//montantT = ancienMontantTravaux + autreAchat.getMontant();
				projet.setTotal(montantTravaux);
				Projet pr =projetRepository.save(projet);
				reste = (pr.getDebousserSec())-(pr.getTotal());
				pr.setReste(reste);
				double percent = 100*(pr.getTotal()/pr.getDebousserSec());
				pr.setPercent(percent);
				projetRepository.save(pr);
			

		//}

		return autreAchat;
	}

	@Override
	public List<AutreAchatTravaux> findAll() {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.findAll();
	}

	@Override
	public AutreAchatTravaux findById(Long id) {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		AutreAchatTravaux autreAchatTravaux = findById(id);
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		Projet projet = projetRepository.findById(autreAchatTravaux.getProjetId()).get();
		montantTravaux = projet.getTotal();
		montantT = montantTravaux - autreAchatTravaux.getMontant();
		projet.setTotal(montantT);
		Projet pr = projetRepository.save(projet);
		reste = pr.getReste() + autreAchatTravaux.getMontant();
		pr.setReste(reste);
		double percent = (pr.getDebousserSec()*pr.getTotal())/100;
		pr.setPercent(percent);
		projetRepository.save(pr);
				autreAchatTravauxRepository.deleteById(id);
                return true; 
	}

	@Override
	public boolean supprimer(List<AutreAchatTravaux> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	

	@Override
	public List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdProjet(long id) {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.getAutreAchatTravauxTravauxByIdProjet(id);
	}

	@Override
	public List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdProjet(long id) {
		return detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxByIdProjet(id);
	}

	@Override
	public Double findDetailAutreAchatTravauxMontantByIdProjet(long id) {
		Double somme = 0d;
		List<DetailAutreAchatTravaux> detailAutreAchatTravauxs = detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxMontantByIdProjet(id);
		for (DetailAutreAchatTravaux detailAutreAchatTravaux : detailAutreAchatTravauxs) {
			somme += detailAutreAchatTravaux.getMontant();
		}
		System.out.println("voir la somme autre achat ++++++"+ somme);
		// TODO Auto-generated method stub
		return somme;
	}

	@Override
	public List<DetailAutreAchatTravaux> getDetailAutreAchatTravauxBydate(long travauxId, LocalDate startDate,
			LocalDate endDate) {
		  
		 List<DetailAutreAchatTravaux>  detailAutreAchatTravaux = detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxByDateBetweenAndProjetId( startDate, endDate,travauxId);
		  
		  
		  return detailAutreAchatTravaux;
	}

	@Override
	public AutreAchatTravaux chercherAutreAchatTravauxParMc(String numeroFacture, long projetId) {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.chercherAutreAchatTravauxParMc(numeroFacture,  projetId);
	}

	@Override
	public Double findAutreAchatTravauxMontantByIdProjet(long id) {
		Double somme = 0d;
		List<AutreAchatTravaux> autreAchatTravauxs = autreAchatTravauxRepository.getAutreAchatTravauxTravauxByIdProjet(id);
		for (AutreAchatTravaux autreAchatTravaux : autreAchatTravauxs) {
			somme += autreAchatTravaux.getMontant();
		}
		System.out.println("voir la somme autre achat ++++++"+ somme);
		// TODO Auto-generated method stub
		return somme;
	}

	@Override
	public List<AutreAchatTravaux> getAutreAchatTravauxBydate(long projetId, LocalDate startDate, LocalDate endDate) {
		List<AutreAchatTravaux>  autreAchatTravaux = autreAchatTravauxRepository.findAutreAchatTravauxByDateBetweenAndProjetId( startDate, endDate, projetId);
		  
		  
		  return autreAchatTravaux;
	}

	
	
}