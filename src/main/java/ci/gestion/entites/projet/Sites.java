package ci.gestion.entites.projet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sites extends AbstractEntity{
	private static final long serialVersionUID = 1L;
	private String nomSites;
	private String description;
	@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
	private Projet projet;
}
