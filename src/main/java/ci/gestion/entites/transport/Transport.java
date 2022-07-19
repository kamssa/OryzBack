package ci.gestion.entites.transport;

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

import ci.gestion.entites.salaire.DetailSalaire;
import ci.gestion.metier.model.DateAudit;

@Entity
public class Transport extends DateAudit {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private LocalDateTime date;
	private double montant=0d;
	private Long projetId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_transport")
	private List<DetailTransport> detailTransport = new ArrayList<>();
	
	
	
	public Transport() {
		super();
	}
	
	public Transport(String libelle, LocalDateTime date, double montant, Long projetId,
			List<DetailTransport> detailTransport) {
		super();
		this.libelle = libelle;
		this.date = date;
		this.montant = montant;
		this.projetId = projetId;
		this.detailTransport = detailTransport;
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
	
	public Long getProjetId() {
		return projetId;
	}

	public void setProjetId(Long projetId) {
		this.projetId = projetId;
	}

	public List<DetailTransport> getDetailTransport() {
		return detailTransport;
	}
	public void setDetailTransport(List<DetailTransport> detailTransport) {
		this.detailTransport = detailTransport;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, detailTransport, id, libelle, montant, projetId, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transport other = (Transport) obj;
		return Objects.equals(date, other.date) && Objects.equals(detailTransport, other.detailTransport)
				&& Objects.equals(id, other.id) && Objects.equals(libelle, other.libelle)
				&& Double.doubleToLongBits(montant) == Double.doubleToLongBits(other.montant)
				&& Objects.equals(projetId, other.projetId) && Objects.equals(version, other.version);
	}

	@Override
	public String toString() {
		return "Transport [id=" + id + ", version=" + version + ", libelle=" + libelle + ", date=" + date + ", montant="
				+ montant + ", projetId=" + projetId + ", detailTransport=" + detailTransport + "]";
	}
	
	
}
