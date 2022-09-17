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
public class Vehicule extends AbstractEntity{

private static final long serialVersionUID = 1L;
private String chauffeur;
private String matriculation;
private String couleur;
private String marque;
@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
private Entreprise entreprise;
}
