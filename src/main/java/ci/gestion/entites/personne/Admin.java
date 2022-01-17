package ci.gestion.entites.personne;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;



@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Personne {

	
	private static final long serialVersionUID = 1L;
   
}