package ci.gestion.entites.mainoeuvre;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.entites.personne.Employe;
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
	private Journalier journalier;
	
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

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public Journalier getJournalier() {
		return journalier;
	}

	public void setJournalier(Journalier journalier) {
		this.journalier = journalier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((journalier == null) ? 0 : journalier.hashCode());
		long temp;
		temp = Double.doubleToLongBits(montantVerser);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(reste);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(salaire);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailMainOeuvre other = (DetailMainOeuvre) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (journalier == null) {
			if (other.journalier != null)
				return false;
		} else if (!journalier.equals(other.journalier))
			return false;
		if (Double.doubleToLongBits(montantVerser) != Double.doubleToLongBits(other.montantVerser))
			return false;
		if (Double.doubleToLongBits(reste) != Double.doubleToLongBits(other.reste))
			return false;
		if (Double.doubleToLongBits(salaire) != Double.doubleToLongBits(other.salaire))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DetailMainOeuvre [id=" + id + ", version=" + version + ", salaire=" + salaire + ", montantVerser="
				+ montantVerser + ", reste=" + reste + ", journalier=" + journalier + "]";
	}

}
