package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.salaire.VersementSalaire;

public interface MontantVerseSalaireRepository extends JpaRepository<VersementSalaire, Long>{

}
