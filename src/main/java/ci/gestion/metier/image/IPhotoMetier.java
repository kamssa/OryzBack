package ci.gestion.metier.image;


import java.util.List;

import ci.gestion.entites.projet.Photo;
import ci.gestion.metier.utilitaire.Imetier;

public interface IPhotoMetier extends Imetier<Photo, Long>{
	List<Photo> findPhotoByIdProjet(long idProjet);
}
