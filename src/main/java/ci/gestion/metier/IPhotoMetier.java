package ci.gestion.metier;


import java.util.List;

import ci.gestion.entites.Photo;

public interface IPhotoMetier extends Imetier<Photo, Long>{
	List<Photo> findPhotoByIdTravaux(long idTravaux);
}
