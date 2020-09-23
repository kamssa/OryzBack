package ci.gestion.entites.salaire;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class DetailSalaire extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@Version
	private Long version;
	private LocalDateTime date;
	private String libelle;
	private double montantVerse;
	private double reste;
	private Long employeId;
	
	
	public DetailSalaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DetailSalaire(LocalDateTime date, String libelle, double montantVerse, double reste, Long employeId) {
		super();
		this.date = date;
		this.libelle = libelle;
		this.montantVerse = montantVerse;
		this.reste = reste;
		this.employeId = employeId;
	}

	public double getMontantVerse() {
		return montantVerse;
	}
	public void setMontantVerse(double montantVerse) {
		this.montantVerse = montantVerse;
	}
	public double getReste() {
		return reste;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public void setReste(double reste) {
		this.reste = reste;
	}
	
	public Long getEmployeId() {
		return employeId;
	}
	public void setEmployeId(Long employeId) {
		this.employeId = employeId;
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
		result = prime * result + ((employeId == null) ? 0 : employeId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montantVerse);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reste);
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
		DetailSalaire other = (DetailSalaire) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (employeId == null) {
			if (other.employeId != null)
				return false;
		} else if (!employeId.equals(other.employeId))
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
		if (Double.doubleToLongBits(montantVerse) != Double.doubleToLongBits(other.montantVerse))
			return false;
		if (Double.doubleToLongBits(reste) != Double.doubleToLongBits(other.reste))
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
		return "DetailSalaire [id=" + id + ", version=" + version + ", date=" + date + ", libelle=" + libelle
				+ ", montantVerse=" + montantVerse + ", reste=" + reste + ", employeId=" + employeId + "]";
	}
	
	
}
