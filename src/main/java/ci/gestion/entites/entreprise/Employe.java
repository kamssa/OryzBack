package ci.gestion.entites.entreprise;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import ci.gestion.entites.personne.Personne;
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

	
	private Boolean activated;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_Depatement")
	private Departement departement;
	@OneToOne(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_Salaire")
	private Salaire Salaire;
	
	

}
