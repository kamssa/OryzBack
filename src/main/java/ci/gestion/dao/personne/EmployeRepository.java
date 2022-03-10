package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.Employe;



public interface EmployeRepository extends JpaRepository<Employe, Long> {
	Optional<Employe> findByEmail(String login);
	@Query("select empl from Employe empl where empl.departement.entreprise.id=?1")
	List<Employe> getEmployeByIdEntreprise(long id);
	@Query("select empl from Employe empl where empl.departement.id=?1")
	List<Employe> getEmployeByIdDepartement(long id);
}
