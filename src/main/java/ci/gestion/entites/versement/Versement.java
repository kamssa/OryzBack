package ci.gestion.entites.versement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.site.Travaux;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Versement extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private LocalDateTime date;
	private String libelle;
	private double solde;
	private double reste;
	@OneToOne(fetch = FetchType.EAGER, cascade =CascadeType.MERGE)
	private Travaux travaux;
	
	
	 
	
}
