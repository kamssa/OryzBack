package ci.gestion.entites.salaire;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.metier.model.DateAudit;

@Entity
public class VersementSalaire extends DateAudit {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	private Long version;
	private double montantVerse;
	private Long employeId;
	
	
	public VersementSalaire() {
		super();
	}
	
		public VersementSalaire(double montantVerse, Long employeId) {
		super();
		this.montantVerse = montantVerse;
		this.employeId = employeId;
	}

		public double getMontantVerse() {
		return montantVerse;
	}
	public void setMontantVerse(double montantVerse) {
		this.montantVerse = montantVerse;
	}
	
	public Long getId() {
		return id;
	}
	public Long getVersion() {
		return version;
	}

	public Long getEmployeId() {
		return employeId;
	}

	public void setEmployeId(Long employeId) {
		this.employeId = employeId;
	}

	@Override
	public String toString() {
		return "VersementSalaire [id=" + id + ", version=" + version + ", montantVerse=" + montantVerse + ", employeId="
				+ employeId + "]";
	}

	
}
