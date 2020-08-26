package ci.gestion.entites.location;

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
public class LocationTravaux extends DateAudit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private LocalDateTime date;
	private double montant=0d;
    private Long travauxId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Location")
	private List<DetailLocation> detailLocation = new ArrayList<>();
	public LocationTravaux() {
		super();
	}
	public LocationTravaux(double montant, List<DetailLocation> detailLocation) {
		super();
		this.montant = montant;
		this.detailLocation = detailLocation;
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
	public List<DetailLocation> getDetailLocation() {
		return detailLocation;
	}
	public void setDetailLocation(List<DetailLocation> detailLocation) {
		this.detailLocation = detailLocation;
	}
	public Long getId() {
		return id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((detailLocation == null) ? 0 : detailLocation.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		LocationTravaux other = (LocationTravaux) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (detailLocation == null) {
			if (other.detailLocation != null)
				return false;
		} else if (!detailLocation.equals(other.detailLocation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "LocationTravaux [id=" + id + ", version=" + version + ", date=" + date + ", montant=" + montant
				+ ", travauxId=" + travauxId + ", detailLocation=" + detailLocation + "]";
	}
	
	
}
