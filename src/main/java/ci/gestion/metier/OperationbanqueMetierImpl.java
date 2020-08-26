package ci.gestion.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return operationBanqueRepository.findAll();
	}

	@Override
	public Operation findById(Long id) {
		// TODO Auto-generated method stub
		return operationBanqueRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
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

}
