package ci.gestion.entites.salaire;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import ci.gestion.entites.operation.Employe;
import ci.gestion.entites.operation.Fournisseur;
import ci.gestion.entites.operation.Materiaux;

@Entity
public class DetailSalaire {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@Version
	private Long version;
	private double montant;
	private double montantVerse;
	private double reste;
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Employe employe;
	
}
