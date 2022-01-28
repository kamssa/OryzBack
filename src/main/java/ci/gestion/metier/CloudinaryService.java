package ci.gestion.metier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Transformation;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.InputStream;
import java.net.URL;
import org.springframework.http.HttpHeaders;

@Service
public class CloudinaryService {
	Cloudinary cloudinary;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, String> valuesMap = new HashMap<>();

	public CloudinaryService() {
		valuesMap.put("cloud_name","hlkmjtn6b");
		valuesMap.put("api_key","719374133526854");
		valuesMap.put("api_secret","d9jYljBgg8N_i2WtBcrwEP4Gsos");
		cloudinary = new Cloudinary(valuesMap);
	}

	public Map upload(MultipartFile multipartFile) throws IOException {
		File file = convert(multipartFile);
		Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return result;
	}
	public Map uploadVideo(MultipartFile multipartFile) throws IOException {
		Map params = ObjectUtils.asMap(
			    "overwrite", true,
			    "resource_type", "video",   
			    "resource_type", "image"
			);
		File file = convert(multipartFile);
		Map uploadResult = cloudinary.uploader().upload(file, params);
		file.delete();
		return uploadResult;
	}
   
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}
	private File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}
	public ResponseEntity<ByteArrayResource> downloadImg(String publicId) {

        logger.info("Requested to download the image file: " + publicId);

        // Generates the URL
        String format = "jpg";
        Transformation transformation = new Transformation().width(100).height(150).crop("fill");
        
        String cloudUrl = cloudinary.url().secure(true).format(format)
                .transformation(transformation)
                .publicId(publicId)
                .generate();

        logger.debug("Generated URL of the image to be downloaded: " + cloudUrl);

        try {
            // Get a ByteArrayResource from the URL
            URL url = new URL(cloudUrl);
            InputStream inputStream = url.openStream();
            byte[] out = org.apache.commons.io.IOUtils.toByteArray(inputStream);
            ByteArrayResource resource = new ByteArrayResource(out);

            // Creates the headers
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.add("content-disposition", "attachment; filename=image.jpg");
            responseHeaders.add("Content-Type", "image/jpg");

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(out.length)
                    // .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);

        } catch (Exception ex) {
            logger.error("FAILED to download the file: " + publicId);
            return null;
        }
    }
}
