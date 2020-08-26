package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.operation.Operation;

public interface OperationBanqueRepository extends JpaRepository<Operation, Long>{

}
