package ci.gestion.dao.detail;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.mainoeuvre.MainOeuvre;

public interface DetailMainOeuvreRepository extends JpaRepository<MainOeuvre, Long> {

}
