package ci.gestion.entites.loyer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;
@Entity
public class Loyer extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private double montant=0d;
	private LocalDateTime date;
    private Long travauxId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Loyer")
	private List<DetailLoyer> detailLoyer = new ArrayList<>();
	public Loyer() {
		super();
	}
	
	public Loyer(String libelle, LocalDateTime date, double montant, Long travauxId, List<DetailLoyer> detailLoyer) {
		super();
		this.libelle = libelle;
		this.date = date;
		this.montant = montant;
		this.travauxId = travauxId;
		this.detailLoyer = detailLoyer;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
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
	public Long getTravauxId() {
		return travauxId;
	}
	public void setTravauxId(Long travauxId) {
		this.travauxId = travauxId;
	}
	public List<DetailLoyer> getDetailLoyer() {
		return detailLoyer;
	}
	public void setDetailLoyer(List<DetailLoyer> detailLoyer) {
		this.detailLoyer = detailLoyer;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((detailLoyer == null) ? 0 : detailLoyer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montant);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((travauxId == null) ? 0 : travauxId.hashCode());
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
		Loyer other = (Loyer) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (detailLoyer == null) {
			if (other.detailLoyer != null)
				return false;
		} else if (!detailLoyer.equals(other.detailLoyer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (Double.doubleToLongBits(montant) != Double.doubleToLongBits(other.montant))
			return false;
		if (travauxId == null) {
			if (other.travauxId != null)
				return false;
		} else if (!travauxId.equals(other.travauxId))
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
		return "Loyer [id=" + id + ", version=" + version + ", libelle=" + libelle + ", montant=" + montant + ", date="
				+ date + ", travauxId=" + travauxId + ", detailLoyer=" + detailLoyer + "]";
	}
	
	
}
