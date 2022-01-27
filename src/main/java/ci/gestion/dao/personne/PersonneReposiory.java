package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.shared.Personne;


public interface PersonneReposiory extends JpaRepository<Personne, Long> {
	// recupere une personne par son nom
	Optional<Personne> findByNom(String nom);
	Optional<Personne> findByEmailOrTelephone(String email, String telephone);
	// liste des personne de la base a partir de id
	List<Personne> findByIdIn(List<Long> userIds);
//	@Query("select pers from Personne pers where pers.email=?1")
	Optional<Personne> findByEmail(String email);

	// verifier si une personne existe a partir de son login
	Boolean existsByEmail(String email);

	

}
