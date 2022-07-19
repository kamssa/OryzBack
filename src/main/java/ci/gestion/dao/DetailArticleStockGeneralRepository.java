package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.stock.DetailAticleStockGeneral;


public interface DetailArticleStockGeneralRepository extends JpaRepository<DetailAticleStockGeneral, Long>{
	@Query("select ds from DetailAticleStockGeneral ds where ds.libelleMateriaux=?1")
	Optional<DetailAticleStockGeneral>  findByLibelleMateriaux(String libelleMateriaux);
	@Query("select dasg from DetailAticleStockGeneral dasg where dasg.entreprise.id=?1")
	List<DetailAticleStockGeneral> getDetailArticleStockGeneralByIdEntreprise(long id);
}
