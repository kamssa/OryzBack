package ci.gestion.metier;

import java.util.List;
import java.util.Optional;

import ci.gestion.entites.entreprise.DetailStockHistory;

public interface DetailStockHistoryMetier extends Imetier<DetailStockHistory, Long>{
	List<DetailStockHistory>  findByLibelleMateriaux(String libelleMateriaux);
}
