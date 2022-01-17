package ci.gestion.entites.site;

import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class Livraison extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	private LocalDateTime dateLivraison;
	@ManyToOne
	private Travaux travaux;
	public Livraison() {
		super();
	}
	
	

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Travaux getTravaux() {
		return travaux;
	}
	public void setTravaux(Travaux travaux) {
		this.travaux = travaux;
	}
	
	
	public LocalDateTime getDateLivraison() {
		return dateLivraison;
	}



	public void setDateLivraison(LocalDateTime dateLivraison) {
		this.dateLivraison = dateLivraison;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((travaux == null) ? 0 : travaux.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livraison other = (Livraison) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (travaux == null) {
			if (other.travaux != null)
				return false;
		} else if (!travaux.equals(other.travaux))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Livraison [id=" + id + ", version=" + version + ", travaux=" + travaux + "]";
	}
	
	
}
