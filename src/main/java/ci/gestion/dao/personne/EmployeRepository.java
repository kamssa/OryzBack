package ci.gestion.dao.personne;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.personne.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long>{

}
