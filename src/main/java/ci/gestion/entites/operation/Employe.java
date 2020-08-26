package ci.gestion.entites.operation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class Employe extends DateAudit{
		private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Version
	private Long version;
    
	private String nom;
	private String prenom;
	private String fonction;
	public Employe() {
		super();
	}
	
	public Employe(String nom, String prenom, String fonction) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.fonction = fonction;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}
	@Override
	public String toString() {
		return "Employe [id=" + id + ", version=" + version + ", nom=" + nom + ", prenom=" + prenom + ", fonction="
				+ fonction + "]";
	}
	
}
