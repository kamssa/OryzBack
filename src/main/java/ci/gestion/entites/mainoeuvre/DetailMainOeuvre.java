package ci.gestion.entites.mainoeuvre;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.entites.operation.Employe;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.metier.model.DateAudit;

@Entity
public class DetailMainOeuvre extends DateAudit{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @Version
	private Long version;
    private double salaire;
	private double montantVerser;
	private double reste;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Employe employe;
	
	public DetailMainOeuvre() {
		super();
	}

	public double getSalaire() {
		return salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	public double getMontantVerser() {
		return montantVerser;
	}

	public void setMontantVerser(double montantVerser) {
		this.montantVerser = montantVerser;
	}

	public double getReste() {
		return reste;
	}

	public void setReste(double reste) {
		this.reste = reste;
	}

	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "DetailMainOeuvre [id=" + id + ", version=" + version + ", salaire=" + salaire + ", montantVerser="
				+ montantVerser + ", reste=" + reste + ", employe=" + employe + "]";
	}
	
	
}
