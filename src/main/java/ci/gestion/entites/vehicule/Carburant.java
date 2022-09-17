package ci.gestion.entites.vehicule;

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
public class Carburant extends AbstractEntity{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
private Double prixUnitaire;
private Double quantite;
private Double total;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Vehicule vehicle;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private StationEssence stationEssence;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Entreprise entreprise;
}
