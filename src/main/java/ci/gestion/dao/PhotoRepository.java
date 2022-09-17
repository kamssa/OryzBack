package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.projet.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	List<Photo> findByIdProjet(long idProjet);
}
