package ci.gestion.entites.vehicule;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Panne extends AbstractEntity{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String typePanne;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Vehicule vehicle;
}
