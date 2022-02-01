package ci.gestion.metier;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.StockRepository;
import ci.gestion.entites.entreprise.Stock;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockMetierImpl implements StockMetier{

	private StockRepository stockRepository;
	@Override
	public Stock creer(Stock entity) throws InvalideOryzException {
   
	BigDecimal prix =	entity.getQuantite().multiply(entity.getMateriel().getPrixUnitaire());
	entity.setPrix(prix);
	BigDecimal montant = prix.add(prix);
	
	return stockRepository.save(entity);
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
		stockRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Stock> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Stock> getStockByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return stockRepository.getStockByIdEntreprise(id);
	}

}
