package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.caisse.CaisseDetail;

public interface CaisseDetailRepository extends JpaRepository<CaisseDetail, Long>{
	@Query("select caisseDetail from CaisseDetail caisseDetail  where caisseDetail.entrepriseId=?1")
	List<CaisseDetail> findCaisseDetailByIdEntreprise(long id);
}
