package ci.gestion.entites.combo;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Materiel extends AbstractEntity{
 
	private String  libelle;
	private String description;
}
