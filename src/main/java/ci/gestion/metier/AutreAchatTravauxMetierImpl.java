package ci.gestion.metier;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.AutreAchatTravauxRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.entites.operation.AutreAchatTravaux;
import ci.gestion.entites.operation.DetailAutreAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutreAchatTravauxMetierImpl implements AutreAchatTravauxMetier{
private AutreAchatTravauxRepository autreAchatTravauxRepository;
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
				sommeMontant += montantD;
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setQuantite(detail.getQuantite());

				autreAchat = autreAchatTravauxRepository.save(entity);
				Travaux travaux = travauxRepository.findById(autreAchat.getTravauxId()).get();
				montantTravaux = travaux.getTotal();
				 montantTravaux += montantD;
				travaux.setTotal(montantTravaux);
				Travaux tr = travauxRepository.save(travaux);
				reste = (tr.getBudget()) - (tr.getTotal());
				tr.setReste(reste);
				travau = travauxRepository.save(tr);
			
			

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
		return null;
	}

	@Override
	public AutreAchatTravaux findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
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

}
