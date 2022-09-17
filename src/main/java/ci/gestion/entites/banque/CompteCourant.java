package ci.gestion.entites.banque;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("CC")
@XmlType(name="CC")
public class CompteCourant extends Compte {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Double decouvert;

public CompteCourant() {
	super();
	// TODO Auto-generated constructor stub
}



public Double getDecouvert() {
	return decouvert;
}

public void setDecouvert(Double decouvert) {
	this.decouvert = decouvert;
}



}
