package ci.gestion.entites.autres;

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

import ci.gestion.entites.loyer.DetailLoyer;
import ci.gestion.metier.model.DateAudit;

@Entity
public class Autres extends DateAudit{
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
	@JoinColumn(name = "fk_Autres")
	private List<DetailAutres> detailAutres = new ArrayList<>();
	
	
	public Autres() {
		super();
	}
	
	public Autres(Long id, Long version, String libelle, double montant, LocalDateTime date, Long travauxId,
			List<DetailAutres> detailAutres) {
		super();
		this.id = id;
		this.version = version;
		this.libelle = libelle;
		this.montant = montant;
		this.date = date;
		this.travauxId = travauxId;
		this.detailAutres = detailAutres;
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
	public List<DetailAutres> getDetailAutres() {
		return detailAutres;
	}
	public void setDetailAutres(List<DetailAutres> detailAutres) {
		this.detailAutres = detailAutres;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}
	
}
