package ci.gestion.entites.operation;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public  class DetailAutreAchatTravaux extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String libelleMateriaux;
	private String unite;
	private double quantite;
	private double prixUnitaire;
	private double montant;
	
	
	}
