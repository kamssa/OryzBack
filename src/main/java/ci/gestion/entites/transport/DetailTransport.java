package ci.gestion.entites.transport;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class DetailTransport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private double montant;
	private LocalDateTime date;
	private Long travauxId;
	
	public DetailTransport() {
		super();
	}
	
	public DetailTransport(String libelle, double montant) {
		super();
		this.libelle = libelle;
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

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public DetailTransport(double montant) {
		super();
		this.montant = montant;
	}

	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "DetailTransport [id=" + id + ", version=" + version + ", montant=" + montant + "]";
	}
	
}
