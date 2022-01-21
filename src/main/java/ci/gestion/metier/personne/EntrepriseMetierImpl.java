package ci.gestion.metier.personne;

import java.util.List;

import org.springframework.stereotype.Service;

import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntrepriseMetierImpl implements IEntrepriseMetier{

	@Override
	public Entreprise creer(Entreprise entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entreprise modifier(Entreprise entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entreprise> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entreprise findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Entreprise> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
