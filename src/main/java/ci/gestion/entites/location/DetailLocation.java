package ci.gestion.entites.location;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.entites.article.Materiaux;
import ci.gestion.entites.fournisseur.Fournisseur;
import ci.gestion.metier.model.DateAudit;

@Entity
public class DetailLocation extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private double montant;
	private LocalDateTime date;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Materiaux materiaux;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Fournisseur fournisseur;
	private Long projetId;
	public DetailLocation() {
		super();
	}
	public DetailLocation(double montant, Materiaux materiaux, Fournisseur fournisseur) {
		super();
		this.montant = montant;
		this.materiaux = materiaux;
		this.fournisseur = fournisseur;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Materiaux getMateriaux() {
		return materiaux;
	}
	public void setMateriaux(Materiaux materiaux) {
		this.materiaux = materiaux;
	}
	
	
	public Long getProjetId() {
		return projetId;
	}
	public void setProjetId(Long projetId) {
		this.projetId = projetId;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	public Long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fournisseur == null) ? 0 : fournisseur.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((materiaux == null) ? 0 : materiaux.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		DetailLocation other = (DetailLocation) obj;
		if (fournisseur == null) {
			if (other.fournisseur != null)
				return false;
		} else if (!fournisseur.equals(other.fournisseur))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (materiaux == null) {
			if (other.materiaux != null)
				return false;
		} else if (!materiaux.equals(other.materiaux))
			return false;
		if (Double.doubleToLongBits(montant) != Double.doubleToLongBits(other.montant))
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
		return "DetailLocation [id=" + id + ", version=" + version + ", montant=" + montant + ", materiaux=" + materiaux
				+ ", fournisseur=" + fournisseur + "]";
	}
	
}
