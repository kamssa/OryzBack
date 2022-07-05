package ci.gestion.entites.versement;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.site.Client;
import ci.gestion.entites.site.Travaux;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DetailVersement extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private LocalDateTime date;
	private double montantVerse;
	@ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.MERGE)
	private Employe employe;
	@ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.MERGE)
	private Travaux travaux;
	
	
	}
