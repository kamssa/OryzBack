package ci.gestion.entites.site;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ci.gestion.entites.shared.Personne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@DiscriminatorValue("CLIENT")
 @AllArgsConstructor
@Data
public class Client extends Personne{
	
	private static final long serialVersionUID = 1L;
	private String libelle;
}
