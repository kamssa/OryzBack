package ci.gestion.entites.autres;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class DetailAutres extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private String designation;
	private Double prixUnitaire;
	private Double quantite;
	private double montant;
	private String nomPrenom;
	private Long travauxId;
}
