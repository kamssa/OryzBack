package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.caisse.Caisse;

public interface CaisseRepository extends JpaRepository<Caisse, Long>{

}
