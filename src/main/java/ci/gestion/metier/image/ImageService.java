package ci.gestion.metier.image;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.ImageRepository;
import ci.gestion.entites.site.Photo;


@Service
@Transactional
public class ImageService {
	@Autowired
	private ImageRepository imageRepository;

	public List<Photo> list() {
		return imageRepository.findByOrderById();
	}

	public void save(Photo image) {
		imageRepository.save(image);
	}
    public Optional<Photo> findById(Long id){
	 return imageRepository.findById(id);
 }
	public void deleteById(Long id) {
		imageRepository.deleteById(id);
	}
	public boolean exists(Long id) {
		return imageRepository.existsById(id);
	}
	public Photo findImageByIdTravaux(Long id) {
		return imageRepository.findImageByIdTravaux(id);
	}

}
