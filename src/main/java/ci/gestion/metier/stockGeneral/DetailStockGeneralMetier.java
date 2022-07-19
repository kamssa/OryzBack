package ci.gestion.metier.stockGeneral;

import java.util.List;

import ci.gestion.entites.stock.DetailAticleStockGeneral;
import ci.gestion.metier.utilitaire.Imetier;

public interface DetailStockGeneralMetier extends Imetier<DetailAticleStockGeneral, Long>{
	List<DetailAticleStockGeneral> getDetailArticleStockGeneralByIdEntreprise(long id);
}
