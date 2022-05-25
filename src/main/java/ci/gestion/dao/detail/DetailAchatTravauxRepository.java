package ci.gestion.dao.detail;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.retraitStock.DetailAchatTravaux;


public interface DetailAchatTravauxRepository extends JpaRepository<DetailAchatTravaux, Long>{
	@Query("select ds from DetailAchatTravaux ds where ds.libelleMateriaux=?1")
	Optional<DetailAchatTravaux>  findByLibelleMateriaux(String libelleMateriaux);
	@Query("select detailAchatTravaux from DetailAchatTravaux detailAchatTravaux  where detailAchatTravaux.travauxId=?1")
	Double findDetailAchatTravauxMontantByIdTravaux(long id);
	@Query("select detailAchatTravaux from DetailAchatTravaux detailAchatTravaux  where detailAchatTravaux.travauxId=?1")
	List<DetailAchatTravaux> findDetailAchatTravauxByIdTravaux(long id);
}
