package ci.gestion.entites.transport;

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
	
	
	public DetailTransport() {
		super();
	}
	
	public DetailTransport(String libelle, double montant) {
		super();
		this.libelle = libelle;
		this.montant = montant;
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
