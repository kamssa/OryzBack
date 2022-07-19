package ci.gestion.dao.detail;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.mainoeuvre.MainOeuvre;

public interface DetailMainOeuvreRepository extends JpaRepository<DetailMainOeuvre, Long> {
	@Query("select detailMainOeuvre from DetailMainOeuvre detailMainOeuvre  where detailMainOeuvre.projetId=?1")
	List<DetailMainOeuvre> findDetailMainOeuvreByIdProjet(long id);
	@Query("select detailMainOeuvre from DetailMainOeuvre detailMainOeuvre  where detailMainOeuvre.projetId=?1")
	List<DetailMainOeuvre> findDetailMainOeuvreMontantByIdProjet(long id);
    List<DetailMainOeuvre> findDetailMainOeuvreByDateBetweenAndProjetId(
			
            @Param("date") LocalDate date,
            @Param("endDate") LocalDate endDate,
            @Param("projetId") Long projetId);
}
