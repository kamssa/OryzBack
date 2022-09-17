package ci.gestion.entites.banque;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.AbstractEntity;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(length=1)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
@Type(name="V",value=VersementBanque.class),
@Type(name="R",value= RetraitBanque.class)
})
@XmlSeeAlso({RetraitBanque.class,VersementBanque.class})
public class Operation extends AbstractEntity {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Double montant;
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="id_Banque")
private Banque banque;
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="id_Compte")
private Compte compte;
@ManyToOne(fetch=FetchType.LAZY)
@JoinColumn(name="id_Employe")
private Employe employe;
@Temporal(TemporalType.DATE)
private Date dateOperation;
public Date getDateOperation() {
	return dateOperation;
}
public void setDateOperation(Date dateOperation) {
	this.dateOperation = dateOperation;
}


public Operation() {
	super();
}
public Operation(Double montant) {
	super();
	this.montant = montant;
}


public Double getMontant() {
	return montant;
}
public void setMontant(Double montant) {
	this.montant = montant;
}
@JsonIgnore
@XmlTransient
public Compte getCompte() {
	return compte;
}
@JsonSetter
public void setCompte(Compte compte) {
	this.compte = compte;
}
@JsonIgnore
@XmlTransient
public Employe getEmploye() {
	return employe;
}
@JsonSetter
public void setEmploye(Employe employe) {
	this.employe = employe;
}

}
