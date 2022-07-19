package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.stock.DetailStockHistory;
import ci.gestion.entites.stock.Stock;

public interface DetailStockHistoryRepository extends JpaRepository<DetailStockHistory, Long>{
	@Query("select ds from DetailStockHistory ds where ds.libelleMateriaux=?1")
	List<DetailStockHistory>  findByLibelleMateriaux(String libelleMateriaux);
	@Query("select detailStockHistory from DetailStockHistory detailStockHistory where detailStockHistory.entreprise.id=?1")
	List<DetailStockHistory> getDetailStockHistorykByIdEntreprise(long id);
}
