package ci.gestion.entites.banque;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ci.gestion.entites.shared.AbstractEntity;
import ci.gestion.entites.site.Banque;
import ci.gestion.metier.model.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation extends AbstractEntity {

	private LocalDate date;
	private String libelle;
	private Double montant;
	private String motif;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_Banque")
	private Banque banque;

	

}
