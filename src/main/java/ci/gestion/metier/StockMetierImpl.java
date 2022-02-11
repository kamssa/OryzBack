package ci.gestion.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.MontantSockRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.dao.detail.DetailStockRepository;
import ci.gestion.entites.entreprise.DetailStock;
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
	@Override
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
						montantD = (detail.getPrixUnitaire()*detail.getQuantite());
						
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
						stock = stockRepository.save(entity);
						
						
				
				}
				
			}else {
				montantD = (detail.getPrixUnitaire()*detail.getQuantite());
				detail.setMontant(montantD);
				sommeMontant += montantD;
				System.out.println("Voir le montant calculer" + detail);
				entity.setMontant(sommeMontant);
				stock = stockRepository.save(entity);
				
					
		    }
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
		Stock stoc;
		MontantStock mt;
		
		stoc = stockRepository.findById(id).get();
	    mt = montantSockRepository.getMontantStockByIdEntreprise(stoc.getEntreprise().getId()).get();
	    double montantMt =	mt.getMontant();
		montantMt-= stoc.getMontant();
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
