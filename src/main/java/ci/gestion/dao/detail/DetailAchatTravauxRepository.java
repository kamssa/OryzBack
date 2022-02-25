package ci.gestion.dao.detail;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import ci.gestion.entites.operation.DetailAchatTravaux;

public interface DetailAchatTravauxRepository extends JpaRepository<DetailAchatTravaux, Long>{
	@Query("select ds from DetailAchatTravaux ds where ds.libelleMateriaux=?1")
	Optional<DetailAchatTravaux>  findByLibelleMateriaux(String libelleMateriaux);
}
