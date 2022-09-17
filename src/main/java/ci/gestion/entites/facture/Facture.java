package ci.gestion.entites.facture;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.fournisseur.Fournisseur;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Facture extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String numero;
	private String type;
	@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
	private Entreprise entreprise;
	
}
