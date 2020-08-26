package ci.gestion.entites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.metier.model.DateAudit;

@Entity
public class Travaux extends DateAudit{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Version
	private Long version;
	
	private String nomChantier;
	private String numeroBon;
	private double accompte;
	private double budget;
	private double reste;
	private double total;
	private LocalDateTime date;
	private LocalDateTime dateLivraison;
	
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Site site;
	
	public Travaux() {
		super();
	}

	public Travaux(String nomChantier, String numeroBon, double accompte, double budget, LocalDateTime date,
			Site site) {
		super();
		this.nomChantier = nomChantier;
		this.numeroBon = numeroBon;
		this.accompte = accompte;
		this.budget = budget;
		this.date = date;
		this.site = site;
	}

	
	public Long getVersion() {
		return version;
	}

	public LocalDateTime getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(LocalDateTime dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public double getReste() {
		return reste;
	}

	public void setReste(double reste) {
		this.reste = reste;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getNomChantier() {
		return nomChantier;
	}

	public void setNomChantier(String nomChantier) {
		this.nomChantier = nomChantier;
	}

	public String getNumeroBon() {
		return numeroBon;
	}

	public void setNumeroBon(String numeroBon) {
		this.numeroBon = numeroBon;
	}

	public double getAccompte() {
		return accompte;
	}

	public void setAccompte(double accompte) {
		this.accompte = accompte;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(accompte);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomChantier == null) ? 0 : nomChantier.hashCode());
		result = prime * result + ((numeroBon == null) ? 0 : numeroBon.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
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
		Travaux other = (Travaux) obj;
		if (Double.doubleToLongBits(accompte) != Double.doubleToLongBits(other.accompte))
			return false;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomChantier == null) {
			if (other.nomChantier != null)
				return false;
		} else if (!nomChantier.equals(other.nomChantier))
			return false;
		if (numeroBon == null) {
			if (other.numeroBon != null)
				return false;
		} else if (!numeroBon.equals(other.numeroBon))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
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
		return "Travaux [id=" + id + ", version=" + version + ", nomChantier=" + nomChantier + ", numeroBon="
				+ numeroBon + ", accompte=" + accompte + ", budget=" + budget + ", date=" + date + ", site=" + site
				+ "]";
	}
	
}
