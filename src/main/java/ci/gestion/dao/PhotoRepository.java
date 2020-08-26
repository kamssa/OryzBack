package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.gestion.entites.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long>{
	//@Query("select photo from Photo ab  where photo.idTravaux=?1")
	List<Photo> findByIdTravaux(long idTravaux);
}
