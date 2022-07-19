package ci.gestion.dao.entreprise;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.Personne;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{
	Optional<Entreprise> findByNom(String nom);

}
