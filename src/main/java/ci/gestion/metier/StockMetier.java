package ci.gestion.metier;

import java.util.List;

import ci.gestion.entites.entreprise.Stock;

public interface StockMetier extends Imetier<Stock, Long>{
	List<Stock> getStockByIdEntreprise(long id);
}
