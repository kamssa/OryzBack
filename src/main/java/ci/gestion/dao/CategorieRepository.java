package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.combo.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{
	@Query("select categorie from Categorie categorie where categorie.idEntreprise=?1")
	List<Categorie> getCategorieByIdEntreprise(long id);
	
}
