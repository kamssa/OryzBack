package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.salaire.Salaire;

public interface SalaireRepository extends JpaRepository<Salaire, Long>{

}
