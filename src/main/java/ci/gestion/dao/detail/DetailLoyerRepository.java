package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.loyer.DetailLoyer;

public interface DetailLoyerRepository extends JpaRepository<DetailLoyer, Long>{
	@Query("select detailLoyer from DetailLoyer detailLoyer  where detailLoyer.travauxId=?1")
	List<DetailLoyer> findDetailLoyerByIdTravaux(long id);
	@Query("select detailLoyer from DetailLoyer detailLoyer  where detailLoyer.travauxId=?1")
	List<DetailLoyer>findDetailLoyerMontantByIdTravaux(long id);
}
