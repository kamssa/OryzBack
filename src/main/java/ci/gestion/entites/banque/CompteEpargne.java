package ci.gestion.entites.banque;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlType;

@Entity
@DiscriminatorValue("CE")
@XmlType(name="CE")
public class CompteEpargne extends Compte {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Double taux;

public CompteEpargne() {
	super();
	// TODO Auto-generated constructor stub
}

public Double getTaux() {
	return taux;
}

public void setTaux(Double taux) {
	this.taux = taux;
}


}
