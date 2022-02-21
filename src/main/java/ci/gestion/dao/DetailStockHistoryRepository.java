package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.DetailStockHistory;

public interface DetailStockHistoryRepository extends JpaRepository<DetailStockHistory, Long>{
	@Query("select ds from DetailStockHistory ds where ds.libelleMateriaux=?1")
	List<DetailStockHistory>  findByLibelleMateriaux(String libelleMateriaux);
}
