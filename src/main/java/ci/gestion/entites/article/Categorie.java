package ci.gestion.entites.article;

import javax.persistence.Entity;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Categorie extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String  libelle;
	private String description;
	private Long idEntreprise;
	
}
