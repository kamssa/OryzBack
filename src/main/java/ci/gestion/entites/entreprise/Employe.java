package ci.gestion.entites.entreprise;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ci.gestion.entites.salaire.Salaire;
import ci.gestion.entites.shared.Personne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@DiscriminatorValue("EMPLOYE")
@NoArgsConstructor @AllArgsConstructor
@Data
public class Employe extends Personne{
	
	private static final long serialVersionUID = 1L;
    private Boolean activated;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_Depatement")
	private Departement departement;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "id_salaire")
	private Salaire salaire;
	
	
}
