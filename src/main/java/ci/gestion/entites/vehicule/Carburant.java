package ci.gestion.entites.vehicule;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;
import javax.persistence.Column;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Carburant extends AbstractEntity{
/**
	 * 
	 */
private static final long serialVersionUID = 1L;
private LocalDate date;
private String nomChauffeur;
private Double prixUnitaire;
private Double quantite;
private Double total;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Vehicule vehicule;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private StationEssence stationEssence;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Entreprise entreprise;

}
