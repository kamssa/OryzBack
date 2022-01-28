package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.combo.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long>{

}
