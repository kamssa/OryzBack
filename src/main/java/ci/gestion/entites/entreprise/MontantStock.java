package ci.gestion.entites.entreprise;

import java.math.BigDecimal;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class MontantStock extends AbstractEntity{

	private static final long serialVersionUID = 1L;
    private BigDecimal montant;

}
