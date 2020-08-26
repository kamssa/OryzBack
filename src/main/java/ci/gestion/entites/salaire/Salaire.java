package ci.gestion.entites.salaire;

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

import ci.gestion.entites.operation.DetailAchatTravaux;
import ci.gestion.metier.model.DateAudit;

@Entity
public class Salaire extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private LocalDateTime date;
	private double montant=0d;
    private Long travauxId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Salaire")
	private List<DetailSalaire> detailSalaire = new ArrayList<>();
	
	
	public Salaire() {
		super();
	}
	
	public Salaire(double montant, List<DetailSalaire> detailSalaire) {
		super();
		this.montant = montant;
		this.detailSalaire = detailSalaire;
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
	public List<DetailSalaire> getDetailSalaire() {
		return detailSalaire;
	}
	public void setDetailSalaire(List<DetailSalaire> detailSalaire) {
		this.detailSalaire = detailSalaire;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}
	
}
