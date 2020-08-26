package ci.gestion.entites.mainoeuvre;

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
public class MainOeuvre extends DateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private double montant = 0d;
	private LocalDateTime date;
	private Long travauxId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "fk_DetailMainOeuvre")
	private List<DetailMainOeuvre> detailMainOeuvre = new ArrayList<>();

	public MainOeuvre() {
		super();
	}

	public MainOeuvre(String libelle, double montant, LocalDateTime date, Long travauxId,
			List<DetailMainOeuvre> detailMainOeuvre) {
		super();
		this.libelle = libelle;
		this.montant = montant;
		this.date = date;
		this.travauxId = travauxId;
		this.detailMainOeuvre = detailMainOeuvre;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Long getTravauxId() {
		return travauxId;
	}

	public void setTravauxId(Long travauxId) {
		this.travauxId = travauxId;
	}

	public List<DetailMainOeuvre> getDetailMainOeuvre() {
		return detailMainOeuvre;
	}

	public void setDetailMainOeuvre(List<DetailMainOeuvre> detailMainOeuvre) {
		this.detailMainOeuvre = detailMainOeuvre;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "MainOeuvre [id=" + id + ", version=" + version + ", libelle=" + libelle + ", montant=" + montant
				+ ", date=" + date + ", travauxId=" + travauxId + ", detailMainOeuvre=" + detailMainOeuvre + "]";
	}

}