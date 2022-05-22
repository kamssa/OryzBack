package ci.gestion.entites.retraitStock;

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
public class AchatTravaux extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	
	private String libelle;
	private LocalDateTime date;
	private double montant=0d;
	private double quantite=0d;

    private Long travauxId;
	@OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_achatTravaux")
	private List<DetailAchatTravaux> detailAchatTravaux = new ArrayList<>();

	public AchatTravaux() {
		super();
	}

	public AchatTravaux(LocalDateTime date, double montant) {
		super();

		this.date = date;
		this.montant = montant;
	}

	public double getQuantite() {
		return quantite;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
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

	public List<DetailAchatTravaux> getDetailAchatTravaux() {
		return detailAchatTravaux;
	}

	public void setDetailAchatTravaux(List<DetailAchatTravaux> detailAchatTravaux) {
		this.detailAchatTravaux = detailAchatTravaux;
	}

	@Override
	public String toString() {
		return "AchatTravaux [id=" + id + ", version=" + version + ", libelle=" + libelle + ", date=" + date
				+ ", montant=" + montant + ", travauxId=" + travauxId + ", detailAchatTravaux=" + detailAchatTravaux
				+ "]";
	}


	
}
