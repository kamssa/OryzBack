package ci.gestion.entites.achat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class AutreAchatTravaux extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	private String libelle;
	private double montant=0d;
	private double quantite=0d;
    private LocalDateTime date;
	private Long travauxId;
    @OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_achatTravaux")
	private List<DetailAutreAchatTravaux> detailAutreAchatTravaux = new ArrayList<>();
}
