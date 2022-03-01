package ci.gestion.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailArticleStockGeneralRepository;
import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.AchatTravauxRepository;
import ci.gestion.dao.detail.DetailAchatTravauxRepository;
import ci.gestion.entites.entreprise.DetailAticleStockGeneral;
import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class AchatTravauxMetierImpl implements IAchatTravauxMetier {
	@Autowired
	private OperationTravauxRepository achatTravauxRepository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private AchatTravauxRepository achaTravauxRepository;
	@Autowired
	private DetailAchatTravauxRepository detailAchatTravauxRepository;
	@Autowired
	private TravauxRepository travauxRepository;
	@Autowired
	private DetailArticleStockGeneralRepository detailArticleStockGeneralRepository;
	@Override
	public AchatTravaux creer(AchatTravaux entity) throws InvalideOryzException {
		AchatTravaux achatTravaux = null;
		AchatTravaux achat = null;
		double montantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		List<DetailAchatTravaux> detailAchats = entity.getDetailAchatTravaux();
		for (DetailAchatTravaux detail : detailAchats) {
			Optional<DetailAchatTravaux> d = detailAchatTravauxRepository.findByLibelleMateriaux(detail.getLibelleMateriaux());
			System.out.println("Voir DetailAchatTravauxr" + d);
			if (d.isPresent()) {
				montantD = ((detail.getPrixUnitaire() * detail.getQuantite()));
				AchatTravaux acht = achaTravauxRepository.getAchatTravauxBylibelle(detail.getLibelleMateriaux());

				double montantDetail = d.get().getMontant();
				double quantite = d.get().getQuantite();
				quantite += detail.getQuantite();
				montantDetail += montantD;
				d.get().setQuantite(quantite);
				d.get().setMontant(montantDetail);
				List<DetailAchatTravaux> detailAchatTravaux = new ArrayList<>();
				detailAchatTravaux.add(d.get());
				double montants = acht.getMontant();
				double quantites = acht.getQuantite();
				
				quantites+= d.get().getQuantite();
				montants += montantD;
				acht.setQuantite(quantites);
				acht.setMontant(montants);
				achat = achaTravauxRepository.save(acht);
				  Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
				  montantTravaux = travaux.getTotal();
					montantT = montantTravaux + montants;
					travaux.setTotal(montantT);
					Travaux tr =travauxRepository.save(travaux);
					reste = (tr.getBudget())-(tr.getTotal());
					       tr.setReste(reste);
					       travauxRepository.save(tr);
				  // stock general
				
					/*
					 * DetailAticleStockGeneral detailSG =
					 * detailArticleStockGeneralRepository.findByLibelleMateriaux(detail.
					 * getLibelleMateriaux()).get();
					 * 
					 * double montantdsg =detailSG.getMontant(); double quantitedsg=
					 * detailSG.getQuantite(); detailSG.setQuantite(quantitedsg); quantitedsg-=
					 * d.get().getQuantite(); montantdsg = quantitedsg * detailSG.getPrixUnitaire();
					 * detailSG.setMontant(montantdsg);
					 * 
					 * detailArticleStockGeneralRepository.save(detailSG);
					 */
				  //stock 

				  
					
			}else {
				montantD = ((detail.getPrixUnitaire()*detail.getQuantite() ));
				detail.setMontant(montantD);
				sommeMontant += montantD;   
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setQuantite(detail.getQuantite());

				achat = achaTravauxRepository.save(entity);
				Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
				  montantTravaux = travaux.getTotal();
					montantT = montantTravaux + sommeMontant;
					travaux.setTotal(montantT);
					Travaux tr =travauxRepository.save(travaux);
					reste = (tr.getBudget())-(tr.getTotal());
					       tr.setReste(reste);
					       travauxRepository.save(tr);
				
			}
			
		}

		return achat;
	}

	@Override
	public AchatTravaux modifier(AchatTravaux modif) throws InvalideOryzException {
		double motantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		double montantModifie = 0;
		Travaux travaux = travauxRepository.findById(modif.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montantModifie = montantTravaux - modif.getMontant();
		System.out.println(" le montant total  reduit" + montantModifie);
		List<DetailAchatTravaux> detailAchats = modif.getDetailAchatTravaux();
		for (DetailAchatTravaux detail : detailAchats) {
			motantD = (detail.getPrixUnitaire() * detail.getQuantite());
			detail.setMontant(motantD);
			sommeMontant += motantD;
		}
		modif.setMontant(sommeMontant);

		AchatTravaux achat = achatTravauxRepository.save(modif);
		montantT = montantModifie + achat.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getBudget()) - (tr.getTotal());
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
		double reste = 0;
		Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montantT = montantTravaux - achat.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = tr.getReste() + achat.getMontant();
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
		double reste = 0;
		double montant1 = 0;
		detailAchatTravauxRepository.deleteById(idDetail);
		AchatTravaux achat = findById(idAchat);
		montantAchat = achat.getMontant();
		System.out.println("Voir montant Achat" + montantAchat);
		Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
		montantTravaux = travaux.getTotal();
		montant = montantTravaux - montantAchat;

		List<DetailAchatTravaux> detailAchats = achat.getDetailAchatTravaux();

		for (DetailAchatTravaux detail : detailAchats) {
			motantD = (detail.getPrixUnitaire() * detail.getQuantite());
			detail.setMontant(motantD);
			sommeMontant += motantD;
			System.out.println("Voir detail" + detail);
		}
		achat.setMontant(sommeMontant);
		AchatTravaux achat1 = achatTravauxRepository.save(achat);

		montantT = montant + achat1.getMontant();
		travaux.setTotal(montantT);
		Travaux tr = travauxRepository.save(travaux);
		reste = (tr.getBudget()) - (tr.getTotal());
		tr.setReste(reste);
		travauxRepository.save(tr);
		AchatTravaux achat2 = achatTravauxRepository.findById(achat1.getId()).get();
		montant1 = achat2.getMontant();
		if (montant1 == 0) {
			achatTravauxRepository.deleteById(achat2.getId());
		}
		return true;
	}

}
