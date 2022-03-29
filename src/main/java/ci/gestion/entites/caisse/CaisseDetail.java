package ci.gestion.entites.caisse;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class CaisseDetail extends AbstractEntity{
    
	private static final long serialVersionUID = 1L;
	private LocalDateTime date;
	private String designation;
	private Double prixUnitaire;
	private Double quantite;
	private double montant;
	private Long entrepriseId;
	private String employe;
	
}
