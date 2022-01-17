package ci.gestion.entites.site;

import javax.persistence.Entity;

import ci.gestion.entites.personne.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Site extends AbstractEntity {

	
	private static final long serialVersionUID = 1L;
	private String nomChantier;
	private String description;
    
}
