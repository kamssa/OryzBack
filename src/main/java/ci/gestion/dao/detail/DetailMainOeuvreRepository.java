package ci.gestion.dao.detail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;

public interface DetailMainOeuvreRepository extends JpaRepository<DetailMainOeuvre, Long> {
	@Query("select detailMainOeuvre from DetailMainOeuvre detailMainOeuvre  where detailMainOeuvre.travauxId=?1")
	List<DetailMainOeuvre> findDetailMainOeuvreByIdTravaux(long id);
	@Query("select detailMainOeuvre from DetailMainOeuvre detailMainOeuvre  where detailMainOeuvre.travauxId=?1")
	List<DetailMainOeuvre> findDetailMainOeuvreMontantByIdTravaux(long id);
}
