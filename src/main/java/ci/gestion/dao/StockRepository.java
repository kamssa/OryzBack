package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
	@Query("select stock from Stock stock where stock.entreprise.id=?1")
	List<Stock> getStockByIdEntreprise(long id);
	@Query("select stock from Stock stock where stock.libelle=?1")
	Stock getStockBylibelle(String libelle);
	
	

}
