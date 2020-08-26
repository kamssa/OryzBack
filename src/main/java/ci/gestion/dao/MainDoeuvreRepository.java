package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import ci.gestion.entites.mainoeuvre.MainOeuvre;

public interface MainDoeuvreRepository extends JpaRepository<MainOeuvre, Long>{
	@Query("select mainOeuvre from MainOeuvre mainOeuvre  where mainOeuvre.travauxId=?1")
	List<MainOeuvre> findMainOeuvreByIdTravaux(long id);
}
