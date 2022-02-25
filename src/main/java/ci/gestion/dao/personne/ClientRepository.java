package ci.gestion.dao.personne;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.site.Client;


public interface ClientRepository extends JpaRepository<Client, Long>{

}
