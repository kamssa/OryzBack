package ci.gestion.metier.achatTravaux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailArticleStockGeneralRepository;
import ci.gestion.dao.OperationTravauxRepository;
import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.dao.detail.AchatTravauxRepository;
import ci.gestion.dao.detail.DetailAchatTravauxRepository;
import ci.gestion.dao.detail.DetailStockRepository;
import ci.gestion.dao.entreprise.EntrepriseRepository;
import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.autres.DetailAutres;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.retraitStock.AchatTravaux;
import ci.gestion.entites.retraitStock.DetailAchatTravaux;
import ci.gestion.entites.site.Projet;
import ci.gestion.entites.stock.DetailAticleStockGeneral;
import ci.gestion.entites.stock.DetailStock;
import ci.gestion.entites.stock.Stock;
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
	private ProjetRepository projetRepository;
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
		Projet projet = null;
		List<DetailAchatTravaux> detailAchats = entity.getDetailAchatTravaux();
		for (DetailAchatTravaux detail : detailAchats) {
			 projet = projetRepository.findById(entity.getProjetId()).get();
			
				List<Stock> ts = stockRepository.getStockByIdEntreprise(projet.getEntreprise().getId());
				for (Stock stoc : ts) {
					if (stoc.getLibelle().equals(entity.getLibelle())) {
						// detail stock
						List<DetailStock> dst = stoc.getDetailStock();
						for (DetailStock details : dst) {
							DetailStock de = detailStockRepository.findByLibelleMateriaux(detail.getLibelleMateriaux()).get();
							double quantite = de.getQuantite();
							if(quantite > detail.getQuantite()) {
								montantD = ((detail.getPrixUnitaire() * detail.getQuantite()));
								detail.setMontant(montantD);
								
								entity.setMontant(montantD);
								//entity.setLibelle(detail.getLibelleMateriaux());
								entity.setQuantite(detail.getQuantite());
                                achat = achaTravauxRepository.save(entity);
                                 
                               // Travaux travaux = travauxRepository.findById(achat.getTravauxId()).get();
                				montantTravaux = projet.getTotal();
                				montantT = montantTravaux + achat.getMontant();
                				projet.setTotal(montantT);
                				Projet pr =projetRepository.save(projet);
                				reste = (pr.getDebousserSec())-(pr.getTotal());
                				pr.setReste(reste);
                				double percent = 100*(pr.getTotal()/pr.getDebousserSec());
                				pr.setPercent(percent);
                				projetRepository.save(pr);
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
												pr.getEntreprise().getId());
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
		Projet projet = projetRepository.findById(modif.getProjetId()).get();
		montantTravaux = projet.getTotal();
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
		projet.setTotal(montantT);
		Projet tr = projetRepository.save(projet);
		reste = (tr.getBudget()) - (tr.getTotal());
		tr.setReste(reste);
		projetRepository.save(tr);
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
		Projet projet = projetRepository.findById(achat.getProjetId()).get();
		montantTravaux = projet.getTotal();
		montantT = montantTravaux - achat.getMontant();
		projet.setTotal(montantT);
		Projet pr = projetRepository.save(projet);
		reste = pr.getReste() + achat.getMontant();
		pr.setReste(reste);
		double percent = (pr.getDebousserSec()*pr.getTotal())/100;
		pr.setPercent(percent);
		projetRepository.save(pr);
		List<Stock> ts = stockRepository.getStockByIdEntreprise(pr.getEntreprise().getId());
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
									pr.getEntreprise().getId());
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
	public List<AchatTravaux> findAchatByIdProjet(long id) {
		// TODO Auto-generated method stub
		return achatTravauxRepository.findAchatByIdProjet(id);
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
		Projet projet = projetRepository.findById(achat.getProjetId()).get();
		montantTravaux = projet.getTotal();
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
		projet.setTotal(montantT);
		Projet pr = projetRepository.save(projet);
		reste = (pr.getBudget()) - (pr.getTotal());
		pr.setReste(reste);
		projetRepository.save(pr);
		AchatTravaux achat2 = achatTravauxRepository.findById(achat1.getId()).get();
		montant1 = achat2.getMontant();
		if (montant1 == 0) {
			achatTravauxRepository.deleteById(achat2.getId());
		}
		return true;
	}

	@Override
	public List<DetailAchatTravaux> findDetailAchatTravauxByIdProjet(long id) {
		// TODO Auto-generated method stub
		return detailAchatTravauxRepository.findDetailAchatTravauxByIdProjet(id);
	}

	@Override
	public Double findDetailAchatTravauxMontantByIdProjet(long id) {
		
			double somme = 0d;
			List<DetailAchatTravaux> detailAchatTravauxs = detailAchatTravauxRepository.findDetailAchatTravauxByIdProjet(id);
			for (DetailAchatTravaux detailAchatTravaux : detailAchatTravauxs) {
				somme += detailAchatTravaux.getMontant();
			}
			System.out.println("voir la somme"+ somme);
			// TODO Auto-generated method stub
			return somme;
		
	}

	@Override
	public List<DetailAchatTravaux> findDetailAchatTravauxByDateIdProjet(long id, LocalDate dateDebut,
			LocalDate dateFin) {
		// TODO Auto-generated method stub
		 List<DetailAchatTravaux>  detailAutreAchatTravaux = detailAchatTravauxRepository.findDetailAchatTravauxByDateBetweenAndProjetId( dateDebut, dateFin,id);
		  
		  
		  return detailAutreAchatTravaux;
	}

	

}
