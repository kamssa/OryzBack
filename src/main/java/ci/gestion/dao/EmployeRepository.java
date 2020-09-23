package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ci.gestion.entites.operation.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long>{

}
