package ci.gestion.metier.stockGeneral;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailArticleStockGeneralRepository;
import ci.gestion.entites.stock.DetailAticleStockGeneral;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetailStockGeneralMetierImpl implements DetailStockGeneralMetier{
private DetailArticleStockGeneralRepository articleStockGeneralRepository;
	@Override
	public DetailAticleStockGeneral creer(DetailAticleStockGeneral entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return articleStockGeneralRepository.save(entity);
	}

	@Override
	public DetailAticleStockGeneral modifier(DetailAticleStockGeneral entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return articleStockGeneralRepository.save(entity);
	}

	@Override
	public List<DetailAticleStockGeneral> findAll() {
		// TODO Auto-generated method stub
		return articleStockGeneralRepository.findAll();
	}

	@Override
	public DetailAticleStockGeneral findById(Long id) {
		// TODO Auto-generated method stub
		return articleStockGeneralRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		articleStockGeneralRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<DetailAticleStockGeneral> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DetailAticleStockGeneral> getDetailArticleStockGeneralByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return articleStockGeneralRepository.getDetailArticleStockGeneralByIdEntreprise(id);
	}

}
