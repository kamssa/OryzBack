package ci.gestion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.gestion.entites.banque.Operation;

public interface OperationBanqueRepository extends JpaRepository<Operation, Long>{
	// ramener tous les Operation par parametre
		@Query("SELECT o FROM Operation o WHERE o.dateOperation BETWEEN :from AND :to OR o.banque.nom = :nom")
		List<Operation> findOperationByParam(
				@Param("from") Date startDay,
                @Param("to") Date endDay,
                @Param("nom") String nom);

}
