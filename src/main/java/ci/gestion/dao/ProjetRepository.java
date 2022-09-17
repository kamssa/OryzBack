package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.projet.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Long>{
	@Query("select pr from Projet pr where pr.libelle LIKE  %?1%  AND pr.entreprise.nom=?2")
	List<Projet> chercherProjetParMc(@Param("libelle") String libelle, String nom);
	
	@Query("select pr from Projet pr where pr.entreprise.id=?1")
	List<Projet> findProjetByIdEntreprise(long id);
	Optional<Projet> findByLibelle(String libelle);
	@Query("select tr from Projet tr  where tr.client.id=?1")
	List<Projet> findProjetByIdClient(long id);

}
