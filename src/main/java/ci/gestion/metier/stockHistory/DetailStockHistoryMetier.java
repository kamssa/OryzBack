package ci.gestion.metier.stockHistory;

import java.util.List;
import java.util.Optional;

import ci.gestion.entites.entreprise.DetailStockHistory;
import ci.gestion.metier.utilitaire.Imetier;

public interface DetailStockHistoryMetier extends Imetier<DetailStockHistory, Long>{
	List<DetailStockHistory>  findByLibelleMateriaux(String libelleMateriaux);
}
