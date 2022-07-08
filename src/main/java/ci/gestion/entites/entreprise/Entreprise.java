package ci.gestion.entites.entreprise;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Entreprise extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	@NaturalId(mutable=true)
	@NotBlank
	@Column(name = "nom", unique=true)
    private String nom;
	private String description;
	private Boolean suspendu;
	private String lienFacebook;
	private String lienLinkedIn;
	private String lienTwitter;
    private String lientInstagram;
    private String logo;
    private String siteWeb;


	
	
}
