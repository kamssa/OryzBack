package ci.gestion.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.DetailArticleStockGeneralRepository;
import ci.gestion.dao.DetailStockHistoryRepository;
import ci.gestion.dao.MontantSockRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.dao.detail.DetailStockRepository;
import ci.gestion.entites.entreprise.DetailAticleStockGeneral;
import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.DetailStockHistory;
import ci.gestion.entites.entreprise.MontantStock;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.entites.operation.Categorie;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockMetierImpl implements StockMetier{

	private StockRepository stockRepository;
	private MontantSockRepository montantSockRepository;
	private DetailStockRepository detailStockRepository;
	private DetailStockHistoryRepository detailStockHistoryRepository;
	private DetailArticleStockGeneralRepository detailArticleStockGeneralRepository;
	@Override
	@Transactional
	public Stock creer(Stock entity) throws InvalideOryzException {
		
		Stock stock = null;
		   MontantStock mt = null;
		 double montantD = 0d;
		 Optional<MontantStock> montantStocks;
			double montantT = 0d;
			double sommeMontant = 0d;
			double reste=0;
			List<DetailStock> detailStock = entity.getDetailStock();
			for(DetailStock detail : detailStock) {
				Optional<DetailStock> d = detailStockRepository.findByLibelleMateriaux(detail.getLibelleMateriaux());
				if(d.isPresent()) {
					System.out.println("Voir detail retour:"+d);
					if(d.get().getLibelleMateriaux().equals(detail.getLibelleMateriaux()) ) {
						System.out.println("Voir si je rentre:");
						montantD = ((detail.getPrixUnitaire()*detail.getQuantite())+ detail.getFrais());
						
						double montantDetail = d.get().getMontant();
						double quantite = d.get().getQuantite();
						quantite += detail.getQuantite();
						montantDetail+= montantD;
						d.get().setQuantite(quantite);
						d.get().setMontant(montantDetail);
						sommeMontant += montantDetail;
						System.out.println("Voir le montant calculer" + detail);
						List<DetailStock> detailStocks = new ArrayList<>();
						detailStocks.add(d.get());
						entity.setDetailStock(detailStocks);
						entity.setMontant(montantD);
						entity.setLibelle(d.get().getLibelleMateriaux());
						entity.setPrixUnitaire(detail.getPrixUnitaire());
						entity.setQuantite(detail.getQuantite());
						stock = stockRepository.save(entity);
						
						 // detailArticleStockGeneral
					List<DetailAticleStockGeneral>	dasg = detailArticleStockGeneralRepository.getDetailArticleStockGeneralByIdEntreprise(stock.getEntreprise().getId());
				     if (!dasg.isEmpty()) {
						for (DetailAticleStockGeneral detailSG : dasg) {
							if (detailSG.getLibelleMateriaux().equals(d.get().getLibelleMateriaux())) {
								double m =(d.get().getMontant())/(d.get().getQuantite());
								double valaur = Math.round(m);
								detailSG.setPrixUnitaire(valaur);
								detailSG.setQuantite(d.get().getQuantite());
								detailSG.setMontant(d.get().getMontant());
								detailArticleStockGeneralRepository.save(detailSG);
							}
						}
					
					}
				  // fin detailArticleStockGeneral
				}
				
			}else {
				montantD = ((detail.getPrixUnitaire()*detail.getQuantite() )+ detail.getFrais());
				detail.setMontant(montantD);
				sommeMontant += montantD;   
				System.out.println("Voir le montant calculer" + detail);
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setPrixUnitaire(detail.getPrixUnitaire());
				entity.setQuantite(detail.getQuantite());
				entity.setFrais(detail.getFrais());
				System.out.println("Voir le stock:" + entity);

                stock = stockRepository.save(entity);
                // detailArticleStockGeneral
			    
				DetailAticleStockGeneral detailSG = new DetailAticleStockGeneral();
				            detailSG.setLibelleMateriaux(detail.getLibelleMateriaux());
				            double m =((sommeMontant)/(detail.getQuantite()));
				            double valaur = Math.round(m);
				            detailSG.setPrixUnitaire(valaur);
				            detailSG.setQuantite(detail.getQuantite());
							detailSG.setMontant(sommeMontant);
							detailSG.setEntreprise(stock.getEntreprise());
							detailArticleStockGeneralRepository.save(detailSG);
						
							System.out.println("Voir le detail S G:====>" + detailSG);
	                // fin detailArticleStockGeneral
				
   			
		   
					
		    }
				// detailStock history
				double montantDH = ((detail.getPrixUnitaire()*detail.getQuantite())+ detail.getFrais());

				DetailStockHistory dsh =new DetailStockHistory();
				dsh.setCategorie(detail.getCategorie());
				 dsh.setLibelleMateriaux(detail.getLibelleMateriaux());
				 dsh.setPrixUnitaire(detail.getPrixUnitaire());
				 dsh.setQuantite(detail.getQuantite());
				 dsh.setMontant(montantDH); 
				  dsh.setUnite(detail.getUnite());
				  dsh.setFrais(detail.getFrais());
				  dsh.setFournisseur(detail.getFournisseur());
				  detailStockHistoryRepository.save(dsh);
	}
			 montantStocks= montantSockRepository.getMontantStockByIdEntreprise(stock.getEntreprise().getId());
			    System.out.println("MontantStocks =>"+montantStocks);
			   if(!montantStocks.isPresent()) {
			    MontantStock mts = new MontantStock();
			    mts.setEntreprise(stock.getEntreprise());
			    montantSockRepository.save(mts);	
		}
		 
			return stock;	
	}

	@Override
	public Stock modifier(Stock entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return stockRepository.save(entity);
	}

	@Override
	public List<Stock> findAll() {
		// TODO Auto-generated method stub
		return stockRepository.findAll();
	}

	@Override
	public Stock findById(Long id) {
		// TODO Auto-generated method stub
		return stockRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		Stock stock;
		DetailStock ds;
		MontantStock mt;
		ds = detailStockRepository.findById(id).get();
		List<Stock> sts = stockRepository.getStockBylibelle(ds.getLibelleMateriaux());
		List<DetailStockHistory> dh = detailStockHistoryRepository.findByLibelleMateriaux(ds.getLibelleMateriaux());
		DetailAticleStockGeneral dsg = detailArticleStockGeneralRepository.findByLibelleMateriaux(ds.getLibelleMateriaux()).get();
		detailArticleStockGeneralRepository.deleteById(dsg.getId());
		detailStockHistoryRepository.deleteAll(dh);
		detailStockRepository.deleteById(id);
		stockRepository.deleteAll(sts);
		stockRepository.deleteById(id);
		
	    return true;
	}

	@Override
	public boolean supprimer(List<Stock> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	@Override
	public List<Stock> getStockByIdEntreprise(long id) {
		 double montant = 0d;
		 List<Stock> stocks ;
		 MontantStock mt = null;
		 stocks = stockRepository.getStockByIdEntreprise(id);
		for (Stock stock : stocks) {
		 montant += stock.getMontant();
		 
		 }
		mt = montantSockRepository.getMontantStockByIdEntreprise(id).get();
		mt.setMontant(montant);
		MontantStock mts = montantSockRepository.save(mt);
		System.out.println(mts);
		return stocks;
	}

}
