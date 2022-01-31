package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import ci.gestion.entites.combo.Materiel;

public interface MaterielRepository extends JpaRepository<Materiel, Long>{
	@Query("select mat from Materiel mat where mat.categorie.id=?1")
	List<Materiel> getMaterielByIdCategorie(long id);
	
}
