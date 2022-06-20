package ci.gestion.metier.autreAchatTravaux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreAchatTravauxRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailAutreAchatTravauxRepository;
import ci.gestion.entites.achat.AutreAchatTravaux;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.mainoeuvre.MainOeuvre;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutreAchatTravauxMetierImpl implements AutreAchatTravauxMetier{
private AutreAchatTravauxRepository autreAchatTravauxRepository;
private DetailAutreAchatTravauxRepository detailAutreAchatTravauxRepository;

private TravauxRepository travauxRepository;
	@Override
	public AutreAchatTravaux creer(AutreAchatTravaux entity) throws InvalideOryzException {
		AutreAchatTravaux autreAchatTravaux = null;
		AutreAchatTravaux autreAchat = null;
		Travaux travau = null;
		double montantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		List<DetailAutreAchatTravaux> detaiAutrelAchats = entity.getDetailAutreAchatTravaux();
		for (DetailAutreAchatTravaux detail : detaiAutrelAchats) {
			
		       montantD = ((detail.getPrixUnitaire() * detail.getQuantite())+ detail.getFrais());
				detail.setMontant(montantD);
				detail.setTravauxId(entity.getTravauxId());
				sommeMontant = montantD;
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setQuantite(detail.getQuantite());

				autreAchat = autreAchatTravauxRepository.save(entity);
				Travaux travaux = travauxRepository.findById(autreAchat.getTravauxId()).get();
				montantTravaux = travaux.getTotal();
				montantT = montantTravaux + autreAchat.getMontant();
				travaux.setTotal(montantT);
				Travaux tr =travauxRepository.save(travaux);
				reste = (tr.getDebousserSec())-(tr.getTotal());
				tr.setReste(reste);
				double percent = 100*(tr.getTotal()/tr.getDebousserSec());
				tr.setPercent(percent);
				travauxRepository.save(tr);
			

		}

		return autreAchat;
	
	}

	@Override
	public AutreAchatTravaux modifier(AutreAchatTravaux entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.save(entity);
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
		Travaux travaux = travauxRepository.findById(autreAchatTravaux.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montantT = montantTravaux - autreAchatTravaux.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = tr.getReste() + autreAchatTravaux.getMontant();
		tr.setReste(reste);
		double percent = (tr.getDebousserSec()*tr.getTotal())/100;
		tr.setPercent(percent);
		travauxRepository.save(tr);
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
	public List<AutreAchatTravaux> getAutreAchatTravauxTravauxByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return autreAchatTravauxRepository.getAutreAchatTravauxTravauxByIdTravaux(id);
	}

	@Override
	public List<DetailAutreAchatTravaux> findDetailAutreAchatTravauxByIdTravaux(long id) {
		return detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxByIdTravaux(id);
	}

	@Override
	public Double findDetailAutreAchatTravauxMontantByIdTravaux(long id) {
		Double somme = 0d;
		List<DetailAutreAchatTravaux> detailAutreAchatTravauxs = detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxMontantByIdTravaux(id);
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
		  
		 List<DetailAutreAchatTravaux>  detailAutreAchatTravaux = detailAutreAchatTravauxRepository.findDetailAutreAchatTravauxByDateBetweenAndTravauxId( startDate, endDate,travauxId);
		  
		  
		  return detailAutreAchatTravaux;
	}
	
}