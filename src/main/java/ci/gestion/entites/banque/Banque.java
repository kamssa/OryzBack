package ci.gestion.entites.banque;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Banque extends AbstractEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom ;
	@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
	private Entreprise entreprise;
	
	
	}
