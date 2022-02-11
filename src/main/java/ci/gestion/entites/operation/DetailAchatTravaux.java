package ci.gestion.entites.operation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.shared.AbstractEntity;

@Entity
public  class DetailAchatTravaux extends AbstractEntity{
	
	
	private double quantite;
	private double prix_unitaire;
	private double frais;
	private double montant;
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	private Materiaux materiaux;
	@ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
	private Fournisseur fournisseur;
	
	
	
	public DetailAchatTravaux() {
		super();
	}
	
	public DetailAchatTravaux(Materiaux materiaux, double quantite, double prix_unitaire,
			double montant, Fournisseur fournisseur) {
		super();
		
		this.materiaux = materiaux;
		this.quantite = quantite;
		this.prix_unitaire = prix_unitaire;
		this.montant = montant;
		this.fournisseur = fournisseur;
	}

	public Long getId() {
		return id;
	}

	public double getFrais() {
		return frais;
	}

	
	public void setFrais(double frais) {
		this.frais = frais;
	}

	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public Materiaux getMateriaux() {
		return materiaux;
	}
	public void setMateriaux(Materiaux materiaux) {
		this.materiaux = materiaux;
	}
	public double getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	public double getPrix_unitaire() {
		return prix_unitaire;
	}
	public void setPrix_unitaire(double prix_unitaire) {
		this.prix_unitaire = prix_unitaire;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	@Override
	public String toString() {
		return "DetailAchatTravaux [id=" + id + ", version=" + version +  ", materiaux="
				+ materiaux + ", quantite=" + quantite + ", prix_unitaire=" + prix_unitaire + ", montant=" + montant
				+ ", fournisseur=" + fournisseur + "]";
	}
	
}
