package ci.gestion.entites.entreprise;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.shared.Adresse;
import ci.gestion.entites.shared.Personne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
@DiscriminatorValue("ENTREPRISE")
public class Entreprise extends Personne{
	
	private static final long serialVersionUID = 1L;
	
	private String description;
	private String logo;
	

	
	
}
