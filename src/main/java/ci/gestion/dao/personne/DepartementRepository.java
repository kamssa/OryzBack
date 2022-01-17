package ci.gestion.dao.personne;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.personne.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long>{

}
