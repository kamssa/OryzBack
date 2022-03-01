package ci.gestion.entites.entreprise;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	private String unite;
	private Double quantite;
	private Double montant;
	private Double frais;
	private String libellefournisseur;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_categorie")
	private Categorie categorie;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_entreprise")
	private Entreprise entreprise;
	
	
}
