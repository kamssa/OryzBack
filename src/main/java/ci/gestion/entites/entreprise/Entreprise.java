package ci.gestion.entites.entreprise;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import ci.gestion.entites.personne.AbstractEntity;
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
	private Boolean activated;
	
	
	
}
