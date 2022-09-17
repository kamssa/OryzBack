package ci.gestion.entites.projet;

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
public class Pays extends AbstractEntity{
	private String nom;
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Ville ville;
}
