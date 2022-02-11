package ci.gestion.entites.operation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Materiaux extends AbstractEntity{
	

	private static final long serialVersionUID = 1L;
	
	private String libelle;
	private String description;
	private String unite;
	private Double prix;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="categorie_id")
    private Categorie categorie;
	
}
