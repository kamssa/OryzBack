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

import com.fasterxml.jackson.annotation.JsonIgnore;

import ci.gestion.entites.achat.DetailAutreAchatTravaux;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.mainoeuvre.DetailMainOeuvre;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.site.Travaux;
import ci.gestion.entites.transport.DetailTransport;
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
	private Travaux travaux;
	
	
	 
	
}
