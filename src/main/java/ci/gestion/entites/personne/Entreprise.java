package ci.gestion.entites.personne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Entreprise extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;

	
	private String nom;
	private String description;
	
	
}
