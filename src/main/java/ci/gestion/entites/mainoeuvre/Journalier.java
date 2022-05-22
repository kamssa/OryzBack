package ci.gestion.entites.mainoeuvre;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class Journalier extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String nom;
	private String Prenom;
	private String nomComplet;
	private String fonction;
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public Long getId() {
		return id;
	}
	
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public Long getVersion() {
		return version;
	}
	
	public String getNomComplet() {
		return nomComplet;
	}
	
	@PrePersist
	@PreUpdate
	public void setNomComplet() {
		this.nomComplet = nom + " " + Prenom;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Prenom == null) ? 0 : Prenom.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Journalier other = (Journalier) obj;
		if (Prenom == null) {
			if (other.Prenom != null)
				return false;
		} else if (!Prenom.equals(other.Prenom))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
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
		return "Journalier [id=" + id + ", version=" + version + ", nom=" + nom + ", Prenom=" + Prenom + "]";
	}
	
}
