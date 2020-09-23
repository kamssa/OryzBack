package ci.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.Banque;

public interface BanqueRepository extends JpaRepository<Banque, Long> {

}
