package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.personne.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {
	Optional<Personne> findByEmail(String email);

	Optional<Personne> findByEmailOrTelephone(String email, String telephone);

	List<Personne> findByIdIn(List<Long> userIds);

	Boolean existsByEmail(String email);
}
