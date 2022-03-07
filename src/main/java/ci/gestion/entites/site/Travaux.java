package ci.gestion.entites.site;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Travaux extends AbstractEntity{
	
	
	private static final long serialVersionUID = 1L;
	private String libelle;
	private String numeroBon;
	private double accompte;
	private double budget;
	private double reste;
	private double total;
	private Double debousserSec;
	private Double percent;
	private LocalDateTime date;
	private LocalDateTime dateLivraison;
	
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Site site;
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Ville ville;
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Client client;
	
	
}
