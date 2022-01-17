package ci.gestion.dao.personne;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.personne.Entreprise;
import ci.gestion.entites.personne.Personne;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{
	Optional<Entreprise> findByNom(String nom);
}
