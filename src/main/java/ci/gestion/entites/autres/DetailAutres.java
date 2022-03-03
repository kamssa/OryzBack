package ci.gestion.entites.autres;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.metier.model.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class DetailAutres extends AbstractEntity{
	
	private LocalDateTime date;
	private String designation;
	private Double prixUnitaire;
	private Double quantite;
	private double montant;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_employe")
	private Employe employe;
	
}
