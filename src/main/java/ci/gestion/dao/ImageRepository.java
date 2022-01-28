package ci.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.gestion.entites.site.Photo;

@Repository
public interface ImageRepository extends JpaRepository<Photo, Long> {
	List<Photo> findByOrderById();

	@Query("select image from Photo image  where image.idTravaux=?1")
	Photo findImageByIdTravaux(Long id);

}
