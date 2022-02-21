package ci.gestion.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailStockHistoryRepository;
import ci.gestion.entites.entreprise.DetailStockHistory;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetailStockHistoryMetierImp implements DetailStockHistoryMetier{
 private DetailStockHistoryRepository detailStockHistoryRepository;
	@Override
	public DetailStockHistory creer(DetailStockHistory entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return detailStockHistoryRepository.save(entity);
	}

	@Override
	public DetailStockHistory modifier(DetailStockHistory entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return detailStockHistoryRepository.save(entity);
	}

	@Override
	public List<DetailStockHistory> findAll() {
		// TODO Auto-generated method stub
		return detailStockHistoryRepository.findAll();
	}

	@Override
	public DetailStockHistory findById(Long id) {
		// TODO Auto-generated method stub
		return detailStockHistoryRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<DetailStockHistory> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DetailStockHistory> findByLibelleMateriaux(String libelleMateriaux) {
		// TODO Auto-generated method stub
		return detailStockHistoryRepository.findByLibelleMateriaux(libelleMateriaux);
	}

}
