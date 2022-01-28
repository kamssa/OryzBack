package ci.gestion.entites.combo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	@OneToMany(mappedBy="categorie")
	private Set<Materiel> materiels = new HashSet<>();
}
