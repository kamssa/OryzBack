package ci.gestion.entites.admin;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ci.gestion.entites.shared.Personne;


@Entity
@DiscriminatorValue("ADMIN")

public class Admin extends Personne {

	
	private static final long serialVersionUID = 1L;
   
}