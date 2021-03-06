package ci.gestion.metier;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.DetailAchatTravauxRepository;
import ci.gestion.entites.Travaux;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class AchatTravauxMetierImpl implements IAchatTravauxMetier{
@Autowired
private OperationTravauxRepository achatTravauxRepository;
@Autowired
private DetailAchatTravauxRepository detailAchatTravauxRepository;
@Autowired
private TravauxRepository travauxRepository;
	@Override
	public AchatTravaux creer(AchatTravaux entity) throws InvalideOryzException {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste=0;
		List<DetailAchatTravaux> detailAchats = entity.getDetailAchatTravaux();
		for(DetailAchatTravaux detail : detailAchats) {
			motantD = (detail.getPrix_unitaire()*detail.getQuantite()+detail.getFrais());
			detail.setMontant(motantD);
			sommeMontant += motantD;
			System.out.println("Voir le montant calculer" + detail);
			}
		entity.setMontant(sommeMontant);
		AchatTravaux achat= achatTravauxRepository.save(entity);
		Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
        montantTravaux = travaux.getTotal();
		montantT = montantTravaux + achat.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return achat;
        }

	@Override
	public AchatTravaux modifier(AchatTravaux modif) throws InvalideOryzException {
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
		List<DetailAchatTravaux> detailAchats = modif.getDetailAchatTravaux();
		for(DetailAchatTravaux detail : detailAchats) {
			motantD = (detail.getPrix_unitaire()*detail.getQuantite()+detail.getFrais());
			detail.setMontant(motantD);
			sommeMontant += motantD;
			}
		modif.setMontant(sommeMontant);
		
             
        AchatTravaux achat= achatTravauxRepository.save(modif);
	    montantT = montantModifie + achat.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return achat;
	}

	@Override
	public List<AchatTravaux> findAll() {
		
		return achatTravauxRepository.findAll();
	}

	@Override
	public AchatTravaux findById(Long id) {
		return achatTravauxRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		AchatTravaux achat = findById(id);
		double montantTravaux = 0;
		double montantT = 0;
		double reste=0;
		Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
        montantTravaux = travaux.getTotal();
		montantT = montantTravaux - achat.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = tr.getReste()+ achat.getMontant();
		       tr.setReste(reste);
		       travauxRepository.save(tr);
				achatTravauxRepository.deleteById(id);
                return true; 
	}

	@Override
	public boolean supprimer(List<AchatTravaux> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AchatTravaux> findAchatByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return achatTravauxRepository.findAchatByIdTravaux(id);
	}

	@Override
	public boolean supprimerDetailAchat(Long idAchat, Long idDetail) {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double montantAchat = 0;
		double montant = 0;
		double reste=0;
		detailAchatTravauxRepository.deleteById(idDetail);
		 AchatTravaux achat = findById(idAchat);
		 montantAchat = achat.getMontant();
		 System.out.println("Voir montant Achat" + montantAchat);
         Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
		 montantTravaux = travaux.getTotal();
		 montant = montantTravaux - montantAchat;
		
	   	List<DetailAchatTravaux> detailAchats = achat.getDetailAchatTravaux();
		 
         for(DetailAchatTravaux detail : detailAchats) {
			motantD = (detail.getPrix_unitaire()*detail.getQuantite()+detail.getFrais());
			detail.setMontant(motantD);
			sommeMontant += motantD;
			System.out.println("Voir detail" + detail);
			}
		achat.setMontant(sommeMontant);
		AchatTravaux achat1= achatTravauxRepository.save(achat);
       
		montantT = montant + achat1.getMontant();
		travaux.setTotal(montantT);
		Travaux tr =travauxRepository.save(travaux);
		reste = (tr.getBudget())-(tr.getTotal());
		       tr.setReste(reste);
		       travauxRepository.save(tr);
		 return true;
	}

}
