package ci.gestion.entites.versement;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class DetailVersement extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private LocalDateTime date;
	private double montantVerse;
	private Long idVersement;
	@ManyToOne(fetch = FetchType.LAZY)
	private Employe employe;
	
	}
