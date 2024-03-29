package ci.gestion.dao.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Departement;


public interface DepartementRepository extends JpaRepository<Departement, Long>{
	@Query("select dep from Departement dep where dep.entreprise.id=?1")
	List<Departement> getDepByIdEntreprise(long id);
	@Query("select dep from Departement dep where dep.libelle=?1")
	Departement getDepByLibelle(String libelle);
	Optional<Departement> findByLibelle(String libelle);
	

}
