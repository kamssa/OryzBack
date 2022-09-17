package ci.gestion.controller;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ci.gestion.entites.projet.Photo;
import ci.gestion.metier.exception.InvalideOryzException;
import ci.gestion.metier.image.CloudinaryService;
import ci.gestion.metier.image.ImageService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UploadController {
	
	@Autowired
	CloudinaryService cloudinaryService;
	@Autowired
	ImageService imageSevice;
	
	
	// solution alterntive cloudinary//////////////////////////
		@PostMapping("/upload")
		public ResponseEntity<?> upload(@RequestParam MultipartFile multipartFile, @RequestParam Long id)
				throws IOException, InvalideOryzException {
			System.out.println(multipartFile);
			Map result = cloudinaryService.upload(multipartFile);
			System.out.println("Voir upload"+ result);
			Photo im = new Photo();
			im.setIdProjet(id);
			im.setImageUrl((String) result.get("url"));
			imageSevice.save(im);
			return new ResponseEntity(result, HttpStatus.OK);
		}

}
