package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.entreprise.DetailStockHistory;

public interface DetailStockHistoryRepository extends JpaRepository<DetailStockHistory, Long>{

}
