package ci.gestion.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.PhotoRepository;
import ci.gestion.entites.Photo;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class PhotoMetierImpl implements IPhotoMetier{
@Autowired
private PhotoRepository photoRepository;
	@Override
	public Photo creer(Photo entity) throws InvalideOryzException {
		return photoRepository.save(entity);
	}

	@Override
	public Photo modifier(Photo entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return photoRepository.save(entity);
	}

	@Override
	public List<Photo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Photo findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Photo> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Photo> findPhotoByIdTravaux(long idTravaux) {
		// TODO Auto-generated method stub
		return photoRepository.findByIdTravaux(idTravaux);
	}

}
