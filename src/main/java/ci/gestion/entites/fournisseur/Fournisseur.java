package ci.gestion.entites.fournisseur;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Fournisseur extends AbstractEntity{
	
	
	private static final long serialVersionUID = 1L;
	private String libelle;
	  
	
}
