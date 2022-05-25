package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.autres.DetailAutres;

public interface DetailAutreRepository extends JpaRepository<DetailAutres, Long>{
	@Query("select detailAutres from DetailAutres detailAutres  where detailAutres.travauxId=?1")
	List<DetailAutres> findDetailAutresByIdTravaux(long id);
	@Query("select detailAutres from DetailAutres detailAutres  where detailAutres.travauxId=?1")
	Double findDetailAutresMontantByIdTravaux(long id);
}
