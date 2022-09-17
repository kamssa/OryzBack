package ci.gestion.entites.projet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class Photo extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	private Long idProjet;
	private String imageUrl;
	private String imageId;


public Photo() {
	super();
}


public Photo(Long id, Long version, Long idProjet, String imageUrl, String imageId) {
	super();
	this.id = id;
	this.version = version;
	this.idProjet = idProjet;
	this.imageUrl = imageUrl;
	this.imageId = imageId;
}


public Long getVersion() {
	return version;
}


public void setVersion(Long version) {
	this.version = version;
}

public Long getIdProjet() {
	return idProjet;
}


public void setIdProjet(Long idProjet) {
	this.idProjet = idProjet;
}


public String getImageUrl() {
	return imageUrl;
}


public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}


public String getImageId() {
	return imageId;
}


public void setImageId(String imageId) {
	this.imageId = imageId;
}


}
