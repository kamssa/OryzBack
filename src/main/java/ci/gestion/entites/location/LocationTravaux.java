package ci.gestion.entites.location;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private LocalDate date;
	private double montant=0d;
	private Long projetId;
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
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	public Long getProjetId() {
		return projetId;
	}
	public void setProjetId(Long projetId) {
		this.projetId = projetId;
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
		return Objects.hash(date, detailLocation, id, libelle, montant, projetId, version);
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
		return Objects.equals(date, other.date) && Objects.equals(detailLocation, other.detailLocation)
				&& Objects.equals(id, other.id) && Objects.equals(libelle, other.libelle)
				&& Double.doubleToLongBits(montant) == Double.doubleToLongBits(other.montant)
				&& Objects.equals(projetId, other.projetId) && Objects.equals(version, other.version);
	}
	@Override
	public String toString() {
		return "LocationTravaux [id=" + id + ", version=" + version + ", libelle=" + libelle + ", date=" + date
				+ ", montant=" + montant + ", projetId=" + projetId + ", detailLocation=" + detailLocation + "]";
	}
	
	
}
