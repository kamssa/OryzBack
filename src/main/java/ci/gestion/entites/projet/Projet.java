package ci.gestion.entites.projet;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import ci.gestion.entites.client.Client;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.shared.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Projet extends AbstractEntity{

	
	private static final long serialVersionUID = 1L;
	private String libelle;
	private String description;
    private String numeroBon;
	private String numeroPojet;
	private double accompte;
	private double budget;
	private double reste;
	private double total;
	private double debousserSec;
	private Double percent;
	private LocalDateTime date;
	private LocalDateTime dateLivraison;
	
	
	
	@ManyToOne(cascade= CascadeType.MERGE, fetch= FetchType.EAGER)
	private Entreprise entreprise;
	@ManyToOne(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
	private Client client;
	@ManyToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinTable(name = "projet_ville", joinColumns = @JoinColumn(name = "projet_id"),
	inverseJoinColumns = @JoinColumn(name = "ville_id"))
	private Set<Ville> ville = new HashSet<>();
	@ManyToMany(fetch = FetchType.LAZY , cascade= CascadeType.ALL)
	@JoinTable(name = "projet_sitGeo", joinColumns = @JoinColumn(name = "projet_id"), 
	inverseJoinColumns = @JoinColumn(name = "sitGeo_id"))
	private Set<SituationGeographique> situationGeographique = new HashSet<>();
	
	
}
