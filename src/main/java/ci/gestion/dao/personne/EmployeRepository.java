package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.projet.Projet;



public interface EmployeRepository extends JpaRepository<Employe, Long> {
	@Query("select pr from Employe pr where pr.nom LIKE  %?1%  AND pr.departement.entreprise.nom=?2")
	List<Employe> chercherEmployeParMc(@Param("nom") String nomEmploye, String nom);
	Optional<Employe> findByEmail(String login);
	@Query("select empl from Employe empl where empl.departement.entreprise.id=?1")
	List<Employe> getEmployeByIdEntreprise(long id);
	@Query("select empl from Employe empl where empl.departement.id=?1")
	List<Employe> getEmployeByIdDepartement(long id);
}
