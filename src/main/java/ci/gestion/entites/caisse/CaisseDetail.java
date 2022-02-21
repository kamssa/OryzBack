package ci.gestion.entites.caisse;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.entreprise.Client;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.entites.operation.Materiaux;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class CaisseDetail extends AbstractEntity{

	
	private LocalDateTime date;
	private String nom;
	private String prenom;
	private double montant;
	private Client client;
	private String designation;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Materiaux materiaux;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Employe employe;
	
}
