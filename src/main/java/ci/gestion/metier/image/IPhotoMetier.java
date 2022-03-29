package ci.gestion.metier.image;


import java.util.List;

import ci.gestion.entites.site.Photo;
import ci.gestion.metier.utilitaire.Imetier;

public interface IPhotoMetier extends Imetier<Photo, Long>{
	List<Photo> findPhotoByIdTravaux(long idTravaux);
}
