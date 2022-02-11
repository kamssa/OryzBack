package ci.gestion.metier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.MontantSockRepository;
import ci.gestion.dao.StockRepository;
import ci.gestion.entites.entreprise.DetailStock;
import ci.gestion.entites.entreprise.MontantStock;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.entites.operation.AchatTravaux;
import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.entites.site.Travaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockMetierImpl implements StockMetier{

	private StockRepository stockRepository;
	private MontantSockRepository montantSockRepository;
	@Override
	public Stock creer(Stock entity) throws InvalideOryzException {
		
		Stock stock;
		 double motantD = 0;
			double montantStock = 0;
			double montantT = 0;
			double sommeMontant = 0;
			double reste=0;
			List<DetailStock> detailStock = entity.getDetailStock();
			for(DetailStock detail : detailStock) {
				motantD = (detail.getPrixUnitaire()*detail.getQuantite());
				detail.setMontant(motantD);
				sommeMontant += motantD;
				System.out.println("Voir le montant calculer" + detail);
				}
			
			 entity.setMontant(sommeMontant);
			stock = stockRepository.save(entity);
		
			return entity;
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
		montantSockRepository.deleteAll();
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
		
		return stockRepository.getStockByIdEntreprise(id);
	}

}
