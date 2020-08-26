package ci.gestion.entites.operation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Fournisseur implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	private String libelle;
	public Fournisseur() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Fournisseur(String libelle) {
		super();
		this.libelle = libelle;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	@Override
	public String toString() {
		return "Fournisseur [id=" + id + ", version=" + version + ", libelle=" + libelle + "]";
	}
	
}
