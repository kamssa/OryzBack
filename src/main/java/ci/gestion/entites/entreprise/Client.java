package ci.gestion.entites.entreprise;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import ci.gestion.entites.shared.Personne;
import lombok.Data;

@Entity
@DiscriminatorValue("CLIENT")
@Data
public class Client extends Personne{
}
