package ci.gestion.metier.banque;

import java.util.Date;
import java.util.List;

import ci.gestion.entites.operation.Operation;
import ci.gestion.metier.utilitaire.Imetier;

public interface IOperationBanqueMetier extends Imetier<Operation, Long>{
	List<Operation> findOperationByParam(Date startDay, Date endDay, String libelle, String nom);

}
