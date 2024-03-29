package ci.gestion.entites.versement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ci.gestion.entites.projet.Projet;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Versement extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	private LocalDateTime date;
	private double solde;
	private double reste;
	@OneToMany(fetch= FetchType.EAGER)
	@JoinColumn(name = "fk_Versement")
	private List<DetailVersement> detailVersement = new ArrayList<>();
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Projet projet;
	
	
	 
	
}
