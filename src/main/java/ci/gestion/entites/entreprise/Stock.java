package ci.gestion.entites.entreprise;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ci.gestion.entites.combo.Materiel;
import ci.gestion.entites.salaire.Salaire;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Stock extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private Materiel materiel;
	private BigDecimal quantite;
	private BigDecimal prixTotal;

}
