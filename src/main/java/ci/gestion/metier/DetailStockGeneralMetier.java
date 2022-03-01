package ci.gestion.metier;

import java.util.List;

import ci.gestion.entites.entreprise.DetailAticleStockGeneral;

public interface DetailStockGeneralMetier extends Imetier<DetailAticleStockGeneral, Long>{
	List<DetailAticleStockGeneral> getDetailArticleStockGeneralByIdEntreprise(long id);
}
