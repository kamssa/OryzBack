package ci.gestion.entites.entreprise;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import ci.gestion.entites.shared.Personne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@DiscriminatorValue("MANAGER")
@NoArgsConstructor @AllArgsConstructor
@Data
public class Manager extends Personne {

	
	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Entreprise")
	private Entreprise entreprise;
	private Boolean actevated;
	
	
}
