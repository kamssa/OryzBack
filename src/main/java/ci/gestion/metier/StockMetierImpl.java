package ci.gestion.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
					if(d.get().getLibelleMateriaux().equals(detail.getLibelleMateriaux()) ) {
						montantD = ((detail.getPrixUnitaire()*detail.getQuantite())+ detail.getFrais());
						Stock stc = stockRepository.getStockBylibelle(detail.getLibelleMateriaux());
						double montantDetail = d.get().getMontant();
						double quantite = d.get().getQuantite();
						quantite += detail.getQuantite();
						montantDetail+= montantD;
						d.get().setQuantite(quantite);
						d.get().setMontant(montantDetail);
						
						List<DetailStock> detailStocks = new ArrayList<>();
						detailStocks.add(d.get());
						stc.setDetailStock(detailStocks);
						double montants = stc.getMontant();
						double quantites = stc.getQuantite();
						
						quantites+= d.get().getQuantite();
						montants += montantD;
						stc.setQuantite(quantites);
						stc.setMontant(montants);
						
						stock = stockRepository.save(stc);
						
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
				entity.setMontant(sommeMontant);
				entity.setLibelle(detail.getLibelleMateriaux());
				entity.setQuantite(detail.getQuantite());

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
				  dsh.setLibellefournisseur(detail.getFournisseur().getLibelle());
				  dsh.setEntreprise(entity.getEntreprise());
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
		Stock stock = null;
		 double montantD = 0d;
		 Optional<MontantStock> montantStocks;
			List<DetailStock> detailStock = entity.getDetailStock();
			for(DetailStock detail : detailStock) {
				Optional<DetailStock> d = detailStockRepository.findByLibelleMateriaux(detail.getLibelleMateriaux());
				if(d.isPresent()) {
					if(d.get().getLibelleMateriaux().equals(detail.getLibelleMateriaux()) ) {
						System.out.println("Voir si je rentre:");
						montantD = ((detail.getPrixUnitaire()*detail.getQuantite())+ detail.getFrais());
						Stock stc = stockRepository.getStockBylibelle(detail.getLibelleMateriaux());
						double montantDetail = d.get().getMontant();
						double quantite = d.get().getQuantite();
						quantite += detail.getQuantite();
						montantDetail+= montantD;
						d.get().setQuantite(quantite);
						d.get().setMontant(montantDetail);
						
						List<DetailStock> detailStocks = new ArrayList<>();
						detailStocks.add(d.get());
						stc.setDetailStock(detailStocks);
						double montants = stc.getMontant();
						double quantites = stc.getQuantite();
						
						quantites+= d.get().getQuantite();
						montants += montantD;
						stc.setQuantite(quantites);
						stc.setMontant(montants);
						
						stock = stockRepository.save(stc);
						
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
				}
				
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
				  dsh.setLibellefournisseur(detail.getFournisseur().getLibelle());
				  dsh.setEntreprise(entity.getEntreprise());
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
	public List<Stock> findAll() {
		
		return stockRepository.findAll();
	}

	@Override
	public Stock findById(Long id) {
		return stockRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		Stock stock = stockRepository.findById(id).get();
		
		
		 List<DetailStockHistory> dsh = detailStockHistoryRepository.getDetailStockHistorykByIdEntreprise(stock.getId());
			for (DetailStockHistory detailAticleStockGeneral : dsh) {
				List<DetailStockHistory> dhs = detailStockHistoryRepository.findByLibelleMateriaux(stock.getLibelle());
				detailStockHistoryRepository.deleteAll(dhs);
			}
	    List<DetailAticleStockGeneral> dsg =	 detailArticleStockGeneralRepository.getDetailArticleStockGeneralByIdEntreprise(stock.getEntreprise().getId());
		for (DetailAticleStockGeneral detailAticleStockGeneral : dsg) {
		  DetailAticleStockGeneral ds = detailArticleStockGeneralRepository.findByLibelleMateriaux(stock.getLibelle()).get();
		  detailArticleStockGeneralRepository.deleteById(ds.getId());
		}
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

	@Override
	public List<Stock> listStockParEntreprise(long id) {

		 List<Stock> dts= stockRepository.getStockByIdEntreprise(id);
	
			return dts;
		
		}
	

}
