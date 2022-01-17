package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.personne.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long>{
	 Optional<Personne> findByEmail(String email);

	    Optional<Personne> findByUsernameOrEmail(String username, String email);

	    List<Personne> findByIdIn(List<Long> userIds);

	    Optional<Personne> findByUsername(String username);

	    Boolean existsByUsername(String username);

	    Boolean existsByEmail(String email);
}
