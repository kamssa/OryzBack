package ci.gestion.metier.stock;

import ci.gestion.entites.entreprise.MontantStock;
import ci.gestion.metier.utilitaire.Imetier;

public interface MontantStockMetier extends Imetier<MontantStock, Long> {
	MontantStock getMontantStockByIdEntreprise(long id);
}
