package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.gestion.entites.banque.Banque;

public interface BanqueRepository extends JpaRepository<Banque, Long> {
	@Query("select v from Banque v where v.entreprise.id=?1")
	List<Banque> getBanqueByIdEntreprise(long id);
}
