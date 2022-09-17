package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.article.Materiaux;

public interface MaterielRepository extends JpaRepository<Materiaux, Long>{
	@Query("select mat from Materiaux mat where mat.categorie.id=?1")
	List<Materiaux> getMaterielByIdCategorie(long id);
	Optional<Materiaux> findByLibelle(String libelle);
}
