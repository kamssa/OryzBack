package ci.gestion.metier.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.MontantSockRepository;
import ci.gestion.entites.stock.MontantStock;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MontantStockMetierImpl implements MontantStockMetier {
	MontantSockRepository montantSockRepository;
	
	@Override
	public MontantStock creer(MontantStock entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return montantSockRepository.save(entity);
	}

	@Override
	public MontantStock modifier(MontantStock entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MontantStock> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MontantStock findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<MontantStock> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MontantStock getMontantStockByIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return montantSockRepository.getMontantStockByIdEntreprise(id).get();
	}

}
