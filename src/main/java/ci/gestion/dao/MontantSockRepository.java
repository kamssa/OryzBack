package ci.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.MontantStock;

public interface MontantSockRepository extends JpaRepository<MontantStock, Long>{
	@Query("select mStock from MontantStock mStock where mStock.stock.id=?1")
	Optional<MontantStock> getMontantStockByIdStock(long id);
}
