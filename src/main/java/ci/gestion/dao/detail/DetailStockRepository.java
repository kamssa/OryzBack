package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.DetailStock;

public interface DetailStockRepository extends JpaRepository<DetailStock, Long>{
	@Query("select ds from DetailStock ds where ds.categorie.id=?1")
	List<DetailStock> getDetailByIdCatgorie(long id);
}
