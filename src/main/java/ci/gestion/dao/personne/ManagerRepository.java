package ci.gestion.dao.personne;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.entreprise.Manager;


public interface ManagerRepository  extends JpaRepository<Manager, Long>{
	@Query("select manage from Manager manage where manage.email=?1")
	Manager getManageByEmail(String email);
}
