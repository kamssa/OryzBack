package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.salaire.DetailSalaire;

public interface DetailSalaireRepository extends JpaRepository<DetailSalaire, Long>{

}
