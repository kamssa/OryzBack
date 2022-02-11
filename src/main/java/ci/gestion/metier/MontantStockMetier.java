package ci.gestion.metier;

import ci.gestion.entites.entreprise.MontantStock;

public interface MontantStockMetier extends Imetier<MontantStock, Long> {
	MontantStock getMontantStockByIdEntreprise(long id);
}
