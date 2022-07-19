package ci.gestion.metier.stock;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.StockRepository;
import ci.gestion.dao.detail.DetailStockRepository;
import ci.gestion.entites.stock.DetailStock;
import ci.gestion.entites.stock.Stock;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetailStockMetierImpl implements DetailStockMetier{
 DetailStockRepository detailStockRepository;
 private StockRepository stockRepository;
	@Override
	public DetailStock creer(DetailStock entity) throws InvalideOryzException {
	    double	montantD;
	    double	somme = 0;
	    montantD = entity.getPrixUnitaire()*entity.getQuantite();
	    entity.setMontant(montantD);
	    somme += montantD;
	   
	 
	    
		return detailStockRepository.save(entity);
	}

	@Override
	public DetailStock modifier(DetailStock entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return detailStockRepository.save(entity);
	}

	@Override
	public List<DetailStock> findAll() {
		// TODO Auto-generated method stub
		return detailStockRepository.findAll();
	}

	@Override
	public DetailStock findById(Long id) {
		// TODO Auto-generated method stub
		return detailStockRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		detailStockRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<DetailStock> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
