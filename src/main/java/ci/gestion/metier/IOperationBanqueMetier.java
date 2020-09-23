package ci.gestion.metier;

import java.util.Date;
import java.util.List;

import ci.gestion.entites.operation.Operation;

public interface IOperationBanqueMetier extends Imetier<Operation, Long>{
	List<Operation> findOperationByParam(Date startDay, Date endDay, String libelle, String nom);

}
