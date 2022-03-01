package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.operation.AutreAchatTravaux;

public interface AutreDetailAchatTravauxRepository extends JpaRepository<AutreAchatTravaux, Long> {

}
