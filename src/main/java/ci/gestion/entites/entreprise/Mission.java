package ci.gestion.entites.entreprise;

import java.time.LocalDate;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.vehicule.Carburant;
import ci.gestion.entites.vehicule.StationEssence;
import ci.gestion.entites.vehicule.Vehicule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Mission extends AbstractEntity{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private String missionnaire;
	private Double fraisMission;
	private String divers;
	private Double nbreJours;
	private String location;

}
