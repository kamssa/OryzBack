package ci.gestion.entites.autres;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class DetailAutres extends DateAudit{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private String libelle;
	private double montant;
	
	public DetailAutres() {
		super();
	}
	
	public DetailAutres(double montant) {
		super();
		this.montant = montant;
	}

	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return "DetailAutres [id=" + id + ", version=" + version + ", montant=" + montant + "]";
	}
	
}
