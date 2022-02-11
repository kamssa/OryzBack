package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.operation.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	@Query("select categorie from Categorie categorie where categorie.idEntreprise=?1")
	List<Categorie> getCategorieByIdEntreprise(long id);
	Optional<Categorie> findByLibelle(String libelle);
	
}
