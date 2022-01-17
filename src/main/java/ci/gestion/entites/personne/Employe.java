package ci.gestion.entites.personne;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import ci.gestion.entites.salaire.Salaire;
import ci.gestion.metier.model.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("EMPLOYE")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Employe extends Personne{
		private static final long serialVersionUID = 1L;

	private String nom;
	private String prenom;
	private String telephone;
	private String fonction;
	private Boolean activated;
	@OneToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Salaire")
	private Salaire Salaire;
	
}
