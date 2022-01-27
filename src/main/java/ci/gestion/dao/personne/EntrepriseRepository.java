package ci.gestion.dao.personne;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.entreprise.Entreprise;


public interface EntrepriseRepository extends JpaRepository<Entreprise, Long>{

}
