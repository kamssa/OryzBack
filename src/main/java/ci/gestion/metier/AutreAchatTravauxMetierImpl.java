package ci.gestion.metier;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.entites.operation.AutreAchatTravaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AutreAchatTravauxMetierImpl implements AutreAchatTravauxMetier{

	@Override
	public AutreAchatTravaux creer(AutreAchatTravaux entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutreAchatTravaux modifier(AutreAchatTravaux entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AutreAchatTravaux> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutreAchatTravaux findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<AutreAchatTravaux> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
