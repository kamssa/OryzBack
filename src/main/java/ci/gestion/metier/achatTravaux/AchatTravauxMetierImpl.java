package ci.gestion.metier.achatTravaux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailArticleStockGeneralRepository;
import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.detail.AchatTravauxRepository;
import ci.gestion.dao.detail.DetailAchatTravauxRepository;
import ci.gestion.dao.detail.DetailStockRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.entreprise.DetailAticleStockGeneral;
import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class AchatTravauxMetierImpl implements IAchatTravauxMetier {
	@Autowired
	private OperationTravauxRepository achatTravauxRepository;
	@Autowired
	private DetailStockRepository detailStockRepository;
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
	@Transactional
	public AchatTravaux creer(AchatTravaux entity) throws InvalideOryzException {
		System.out.println("entre  Achat Travaux:>>>>>>>" + entity);
		AchatTravaux achat = null;
		double montantD = 0;
		double montantTravaux = 0;
		double montantT = 0;
		double sommeMontant = 0;
		double reste = 0;
		List<DetailAchatTravaux> detailAchats = entity.getDetailAchatTravaux();
		for (DetailAchatTravaux detail : detailAchats) {
			Optional<DetailAchatTravaux> d = detailAchatTravauxRepository
					.findByLibelleMateriaux(detail.getLibelleMateriaux());
			System.out.println("Voir Detail Achat Travauxr: &&&&&&&&&&" + d);
			if (d.isPresent()) {
				double quantity = d.get().getQuantite();
				
				quantity += detail.getQuantite();
				montantD = ((detail.getPrixUnitaire() * quantity));
				AchatTravaux stc = achaTravauxRepository.getAchatTravauxBylibelle(detail.getLibelleMateriaux());
                d.get().setTravauxId(entity.getTravauxId());
				d.get().setQuantite(quantity);
				d.get().setMontant(montantD);
				d.get().setPrixUnitaire(detail.getPrixUnitaire());
				List<DetailAchatTravaux> detailAchatTravaux = new ArrayList<>();
				detailAchatTravaux.add(d.get());
				entity.setDetailAchatTravaux(detailAchatTravaux);

				stc.setLibelle(detail.getLibelleMateriaux());
				stc.setQuantite(quantity);
				stc.setMontant(montantD);

				Travaux trav = travauxRepository.findById(entity.getTravauxId()).get();
				// stock
				System.out.println("Voir id de l'entreprise: ~~~~~~~~~~~~>" + trav.getSite());
				List<Stock> ts = stockRepository.getStockByIdEntreprise(trav.getSite().getEntreprise().getId());
				for (Stock stoc : ts) {
					if (stoc.getLibelle().equals(entity.getLibelle())) {
						// detail stock
						List<DetailStock> dst = stoc.getDetailStock();
						for (DetailStock details : dst) {
							DetailStock de = detailStockRepository.findByLibelleMateriaux(detail.getLibelleMateriaux()).get();
							double quantite = de.getQuantite();
							if(quantite > detail.getQuantite()) {
								achat = achaTravauxRepository.save(stc);

								Travaux travaux = travauxRepository.findById(entity.getTravauxId()).get();
								montantTravaux = travaux.getTotal();
								double montantDetail = (detail.getPrixUnitaire() * detail.getQuantite());
								montantTravaux += montantDetail;
								travaux.setTotal(montantTravaux);
								Travaux tr = travauxRepository.save(travaux);
								System.out.println("Voir si travaux est recuperer: ~~~~~~~~~~~~>" + tr);
								reste = (tr.getDebousserSec())-(tr.getTotal());
								tr.setReste(reste);
								double percent = 100*(tr.getTotal()/tr.getDebousserSec());
								tr.setPercent(percent);
								Travaux trava = travauxRepository.save(tr);
								quantite -= detail.getQuantite();
								double prixUnitaire = detail.getPrixUnitaire();
								montantD = ((quantite * prixUnitaire));
								de.setFrais(0d);
								de.setQuantite(quantite);
								de.setPrixUnitaire(detail.getPrixUnitaire());
								de.setMontant(montantD);
								// detail stock
								
								stoc.setMontant(montantD);
								stoc.setQuantite(quantite);
								List<DetailStock> detailStocks = new ArrayList<>();
								detailStocks.add(de);
								stoc.setDetailStock(detailStocks);
								stockRepository.save(stoc);
								// stock general
								// detailArticleStockGeneral
								List<DetailAticleStockGeneral> dasg = detailArticleStockGeneralRepository
										.getDetailArticleStockGeneralByIdEntreprise(
												tr.getSite().getEntreprise().getId());
								if (!dasg.isEmpty()) {
									for (DetailAticleStockGeneral detailSG : dasg) {
										if (detailSG.getLibelleMateriaux().equals(detail.getLibelleMateriaux())) {
										
											detailSG.setQuantite(de.getQuantite());
											detailSG.setMontant(de.getMontant());
											detailArticleStockGeneralRepository.save(detailSG);
										}
									}

								}
							}else {
								throw new InvalideOryzException("stock insuffisant");
							}
							
							
						}

					}

				}


			} else {
				montantD = ((detail.getPrixUnitaire() * detail.getQuantite()));
				detail.setTravauxId(entity.getTravauxId());
				detail.setMontant(montantD);
				sommeMontant += montantD;
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setQuantite(detail.getQuantite());
				
				Travaux trav = travauxRepository.findById(entity.getTravauxId()).get();
				// stock
				List<Stock> ts = stockRepository.getStockByIdEntreprise(trav.getSite().getEntreprise().getId());
				for (Stock stoc : ts) {
					if (stoc.getLibelle().equals(entity.getLibelle())) {
						// detail stock
						List<DetailStock> dst = stoc.getDetailStock();
						for (DetailStock details : dst) {
							DetailStock de = detailStockRepository.findByLibelleMateriaux(detail.getLibelleMateriaux()).get();
							double quantite = de.getQuantite();
							if(quantite > detail.getQuantite()) {
								
								achat = achaTravauxRepository.save(entity);
								Travaux travaux = travauxRepository.findById(entity.getTravauxId()).get();
								montantTravaux = travaux.getTotal();
								montantTravaux += montantD;
								travaux.setTotal(montantTravaux);
								Travaux tr = travauxRepository.save(travaux);
								reste = (tr.getDebousserSec())-(tr.getTotal());
								tr.setReste(reste);
								double percent = 100*(tr.getTotal()/tr.getDebousserSec());
								tr.setPercent(percent);
								Travaux trava = travauxRepository.save(tr);
								quantite -= detail.getQuantite();
								double prixUnitaire = detail.getPrixUnitaire();
								montantD = ((quantite * prixUnitaire) );
								de.setFrais(0d);
								de.setQuantite(quantite);
								de.setPrixUnitaire(detail.getPrixUnitaire());
								de.setMontant(montantD);
								// detail stock
								
								stoc.setMontant(montantD);
								stoc.setQuantite(quantite);
								List<DetailStock> detailStocks = new ArrayList<>();
								detailStocks.add(de);
								stoc.setDetailStock(detailStocks);
								stockRepository.save(stoc);
								// stock general
								// detailArticleStockGeneral
								List<DetailAticleStockGeneral> dasg = detailArticleStockGeneralRepository
										.getDetailArticleStockGeneralByIdEntreprise(
												tr.getSite().getEntreprise().getId());
								if (!dasg.isEmpty()) {
									for (DetailAticleStockGeneral detailSG : dasg) {
										if (detailSG.getLibelleMateriaux().equals(de.getLibelleMateriaux())) {
										
											detailSG.setPrixUnitaire(detail.getPrixUnitaire());
											detailSG.setQuantite(de.getQuantite());
											detailSG.setMontant(de.getMontant());
											detailArticleStockGeneralRepository.save(detailSG);
										}
									}

								}
							}else {
								throw new InvalideOryzException("stock insuffisant");
							}
						
							// fin detailArticleStockGeneral

							

						}

					}

				}

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
		System.out.println("Voir achat:"+achat);
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
		double percent = (tr.getDebousserSec()*tr.getTotal())/100;
		tr.setPercent(percent);
		travauxRepository.save(tr);
		List<Stock> ts = stockRepository.getStockByIdEntreprise(tr.getSite().getEntreprise().getId());
		for (Stock stoc : ts) {
			if (stoc.getLibelle().equals(achat.getLibelle())) {
				// detail stock
				List<DetailStock> dst = stoc.getDetailStock();
				for (DetailStock details : dst) {
					DetailStock de = detailStockRepository.findByLibelleMateriaux(achat.getLibelle()).get();
					double quantite = de.getQuantite();
					quantite += achat.getQuantite();
					double prixUnitaire = de.getPrixUnitaire();
					double montantD = ((quantite * prixUnitaire) );
					de.setFrais(0d);
					de.setQuantite(quantite);
					de.setPrixUnitaire(de.getPrixUnitaire());
					de.setMontant(montantD);
					// detail stock
					
					stoc.setMontant(montantD);
					stoc.setQuantite(quantite);
					List<DetailStock> detailStocks = new ArrayList<>();
					detailStocks.add(de);
					stoc.setDetailStock(detailStocks);
					stockRepository.save(stoc);
					// stock general
					// detailArticleStockGeneral
					List<DetailAticleStockGeneral> dasg = detailArticleStockGeneralRepository
							.getDetailArticleStockGeneralByIdEntreprise(
									tr.getSite().getEntreprise().getId());
					if (!dasg.isEmpty()) {
						for (DetailAticleStockGeneral detailSG : dasg) {
							if (detailSG.getLibelleMateriaux().equals(achat.getLibelle())) {
							
								detailSG.setQuantite(de.getQuantite());
								detailSG.setMontant(de.getMontant());
								detailArticleStockGeneralRepository.save(detailSG);
							}
						}

					}
					
				}

			}

		}

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

	@Override
	public List<DetailAchatTravaux> findDetailAchatTravauxByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return detailAchatTravauxRepository.findDetailAchatTravauxByIdTravaux(id);
	}

	@Override
	public Double findDetailAchatTravauxMontantByIdTravaux(long id) {
		
			double somme = 0d;
			List<DetailAchatTravaux> detailAchatTravauxs = detailAchatTravauxRepository.findAll();
			for (DetailAchatTravaux detailAchatTravaux : detailAchatTravauxs) {
				somme += detailAchatTravaux.getMontant();
			}
			System.out.println("voir la somme"+ somme);
			// TODO Auto-generated method stub
			return somme;
		
	}

	@Override
	public List<DetailAchatTravaux> findDetailAchatTravauxByDateIdTravaux(long id, LocalDateTime dateDebut,
			LocalDateTime dateFin) {
		// TODO Auto-generated method stub
		return detailAchatTravauxRepository.findDetailAchatTravauxByDateIdTravaux(id, dateDebut, dateFin);
	}

}
