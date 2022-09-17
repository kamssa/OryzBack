package ci.gestion.entites.banque;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.AbstractEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE_CPTE", discriminatorType = DiscriminatorType.STRING, length = 2)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(name = "CC", value = CompteCourant.class), @Type(name = "CE", value = CompteEpargne.class) })
@XmlSeeAlso({ CompteCourant.class, CompteEpargne.class })
public abstract class Compte extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double numero;
    private Double solde;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODE_BANK")
	private Banque banque;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODE_EMP")
	private Employe employe;
	

	/*
	 * @OneToMany(mappedBy="compte") private Collection<Operation> operations;
	 */
	public Compte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Compte( Double solde) {
		super();
		
		this.solde = solde;

	}

	


	public Double getNumero() {
		return numero;
	}

	public void setNumero(Double numero) {
		this.numero = numero;
	}

	public Banque getBanque() {
		return banque;
	}

	public void setBanque(Banque banque) {
		this.banque = banque;
	}

	public Double getSolde() {
		return solde;
	}

	public void setSolde(Double solde) {
		this.solde = solde;
	}

	
	public Employe getEmploye() {
		return employe;
	}

	public void setEmploye(Employe employe) {
		this.employe = employe;
	}

	

	/*
	 * @JsonIgnore
	 * 
	 * @XmlTransient public Collection<Operation> getOperations() { return
	 * operations; } public void setOperations(Collection<Operation> operations)
	 * { this.operations = operations; }
	 */
	

}
