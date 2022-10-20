package ci.gestion.entites.achat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import ci.gestion.entites.shared.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class AutreAchatTravaux extends AbstractEntity{
	
	private static final long serialVersionUID = 1L;
	@Column(unique=true)
	private String numeroFacture;
    private String libelle;
	private double montant=0d;
	private double total=0d;
	private String fournisseur;
    private LocalDate date;
    private Long projetId;
    @OneToMany(fetch= FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name = "fk_achatTravaux")
	private List<DetailAutreAchatTravaux> detailAutreAchatTravaux = new ArrayList<>();
}
