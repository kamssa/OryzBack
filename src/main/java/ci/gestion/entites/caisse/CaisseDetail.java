package ci.gestion.entites.caisse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.entites.operation.Materiaux;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.site.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class CaisseDetail extends AbstractEntity{

	
	private LocalDateTime date;
	private String designation;
	private double montant;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_Employe")
	private Employe employe;
	
}
