package ci.gestion.entites;

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
	private Long idTravaux;
    private String path;


public Photo() {
	super();
}

public Photo(String path) {
	super();
	this.path = path;
}

public Long getIdTravaux() {
	return idTravaux;
}

public void setIdTravaux(Long idTravaux) {
	this.idTravaux = idTravaux;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}

public Long getId() {
	return id;
}

public Long getVersion() {
	return version;
}

@Override
public String toString() {
	return "Photo [id=" + id + ", version=" + version + ", path=" + path + ", idTravaux=" + idTravaux + "]";
}

}
