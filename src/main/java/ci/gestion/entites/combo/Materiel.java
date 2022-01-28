package ci.gestion.entites.combo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Materiel extends AbstractEntity{
 

	private static final long serialVersionUID = 1L;
	private String  libelle;
	private String description;
	@ManyToOne
    @JoinColumn(name="categorie_id", nullable=false)
    private Categorie categorie;
}
