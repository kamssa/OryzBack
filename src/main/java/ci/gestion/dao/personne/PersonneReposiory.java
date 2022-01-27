package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.shared.Personne;


public interface PersonneReposiory extends JpaRepository<Personne, Long> {
	// recupere une personne par son nom
	Optional<Personne> findByNom(String nom);
	Optional<Personne> findByEmailOrTelephone(String email, String telephone);
	// liste des personne de la base a partir de id
	List<Personne> findByIdIn(List<Long> userIds);

	Optional<Personne> findByEmail(String login);

	// verifier si une personne existe a partir de son login
	Boolean existsByEmail(String email);

	

}
