package ci.gestion.entites.loyer;

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
public class Loyer extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private double montant=0d;
	private LocalDateTime date;
	private Long projetId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Loyer")
	private List<DetailLoyer> detailLoyer = new ArrayList<>();
	public Loyer() {
		super();
	}
	
	public Loyer(String libelle, LocalDateTime date, double montant, Long projetId, List<DetailLoyer> detailLoyer) {
		super();
		this.libelle = libelle;
		this.date = date;
		this.montant = montant;
		this.projetId = projetId;
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
	
	public Long getProjetId() {
		return projetId;
	}

	public void setProjetId(Long projetId) {
		this.projetId = projetId;
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
		return Objects.hash(date, detailLoyer, id, libelle, montant, projetId, version);
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
		return Objects.equals(date, other.date) && Objects.equals(detailLoyer, other.detailLoyer)
				&& Objects.equals(id, other.id) && Objects.equals(libelle, other.libelle)
				&& Double.doubleToLongBits(montant) == Double.doubleToLongBits(other.montant)
				&& Objects.equals(projetId, other.projetId) && Objects.equals(version, other.version);
	}

	@Override
	public String toString() {
		return "Loyer [id=" + id + ", version=" + version + ", libelle=" + libelle + ", montant=" + montant + ", date="
				+ date + ", projetId=" + projetId + ", detailLoyer=" + detailLoyer + "]";
	}

	
}
