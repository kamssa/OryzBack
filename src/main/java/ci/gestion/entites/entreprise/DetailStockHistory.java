package ci.gestion.entites.entreprise;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ci.gestion.entites.operation.Categorie;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailStockHistory extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	private String libelleMateriaux;
	private Double prixUnitaire;
	private Double quantite;
	private Double montant;
	private String unite;
	private Double frais;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_categorie")
	private Categorie categorie;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Fournisseur fournisseur;
	
	
	
}
