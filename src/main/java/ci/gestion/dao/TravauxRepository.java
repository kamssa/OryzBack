package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.site.Site;
import ci.gestion.entites.site.Travaux;

public interface TravauxRepository extends JpaRepository<Travaux, Long>{
	@Query("select travaux from Travaux travaux where travaux.site.nomChantier LIKE %?1%")
	List<Travaux> chercherTravauxParMc(@Param("nomChantier") String mc);
	@Query("select travaux from Travaux travaux where travaux.site.nomChantier LIKE %?1% AND travaux.site.entreprise.id=?2")
	List<Travaux> chercherTravauxParSiteMc(@Param("nomChantier") String mc);
	@Query("select tr from Travaux tr  where tr.site.id=?1")
	List<Travaux> findTravauxByIdSite(long id);
	@Query("select travaux from Travaux travaux where travaux.site.nomChantier LIKE %?1%")
	List<Travaux> findTravauxByIdEntreprise(@Param("nomChantier") String mc);
	Optional<Travaux> findByLibelle(String libelle);

	
}
