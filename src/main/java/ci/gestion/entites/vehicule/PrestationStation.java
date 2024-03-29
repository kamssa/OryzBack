package ci.gestion.entites.vehicule;

import java.time.LocalDate;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class PrestationStation extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private String libelle;
	private Double prixSuper;
	private Double prixGazoil;
	private Double prixVidange;
	private Double prixHuileMoteur;
}
