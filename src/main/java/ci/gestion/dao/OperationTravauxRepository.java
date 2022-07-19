package ci.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.retraitStock.AchatTravaux;

public interface OperationTravauxRepository extends JpaRepository<AchatTravaux, Long> {
	Optional<AchatTravaux> findByLibelle(String libelle);

	// recupere les achat par id travaux
	@Query("select achat from AchatTravaux achat  where achat.projetId=?1")
	List<AchatTravaux> findAchatByIdProjet(long id);
	
}
