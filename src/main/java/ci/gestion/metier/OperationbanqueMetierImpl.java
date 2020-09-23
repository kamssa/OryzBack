package ci.gestion.metier;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ci.gestion.dao.OperationBanqueRepository;
import ci.gestion.entites.operation.Operation;
import ci.gestion.metier.exception.InvalideOryzException;
@Service
public class OperationbanqueMetierImpl implements IOperationBanqueMetier {
@Autowired
private OperationBanqueRepository operationBanqueRepository;
	@Override
	public Operation creer(Operation entity) throws InvalideOryzException {
		
		return operationBanqueRepository.save(entity);
	}

	@Override
	public Operation modifier(Operation entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return operationBanqueRepository.save(entity);
	}

	@Override
	public List<Operation> findAll() {
		// TODO Auto-generated method stub
		return operationBanqueRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public Operation findById(Long id) {
		// TODO Auto-generated method stub
		return operationBanqueRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		operationBanqueRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Operation> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Operation> findOperationByParam(Date startDay, Date endDay, String libelle, String nom) {
		return operationBanqueRepository.findOperationByParam(startDay, endDay, libelle, nom);
	}
	
}
