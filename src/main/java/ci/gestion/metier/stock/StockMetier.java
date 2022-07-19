package ci.gestion.metier.stock;

import java.util.List;

import ci.gestion.entites.stock.Stock;
import ci.gestion.metier.utilitaire.Imetier;

public interface StockMetier extends Imetier<Stock, Long>{
	List<Stock> getStockByIdEntreprise(long id);
	 List<Stock> listStockParEntreprise(long id);
}
