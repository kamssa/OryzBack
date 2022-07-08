package ci.gestion.entites.site;

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
public class Ville extends AbstractEntity{
 public String nom;
 public String description;
 @ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
private Projet projet;
 
}
