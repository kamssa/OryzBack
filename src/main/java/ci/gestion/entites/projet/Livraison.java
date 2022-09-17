package ci.gestion.entites.projet;

import java.time.LocalDateTime;
import java.util.Objects;

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
	private Projet projet;
	public Livraison() {
		super();
	}
	
	

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	public Projet getProjet() {
		return projet;
	}



	public void setProjet(Projet projet) {
		this.projet = projet;
	}



	public LocalDateTime getDateLivraison() {
		return dateLivraison;
	}



	public void setDateLivraison(LocalDateTime dateLivraison) {
		this.dateLivraison = dateLivraison;
	}



	@Override
	public int hashCode() {
		return Objects.hash(dateLivraison, id, projet, version);
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
		return Objects.equals(dateLivraison, other.dateLivraison) && Objects.equals(id, other.id)
				&& Objects.equals(projet, other.projet) && Objects.equals(version, other.version);
	}



	@Override
	public String toString() {
		return "Livraison [id=" + id + ", version=" + version + ", dateLivraison=" + dateLivraison + ", projet="
				+ projet + "]";
	}



	
	
}
