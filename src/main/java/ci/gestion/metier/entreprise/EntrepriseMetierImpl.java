package ci.gestion.metier.entreprise;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.entreprise.EntrepriseRepository;
import ci.gestion.entites.entreprise.Entreprise;
import ci.gestion.entites.shared.Personne;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EntrepriseMetierImpl implements EntrepriseMetier{
	private EntrepriseRepository entrepriseRepository;
	private PasswordEncoder passwordEncoder;
	
	@Override
    public Entreprise creer(Entreprise entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		if ((entity.getNom().equals(null)) || (entity.getNom() == "")) {
			throw new InvalideOryzException("Le nom ne peut etre null");
		}
		
		Optional<Entreprise> pers = null;

		pers = entrepriseRepository.findByNom(entity.getNom());
		if (pers.isPresent()) {
			throw new InvalideOryzException("Ce nom est déjà utilisé");
		}
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		return entrepriseRepository.save(entity);
	}

	@Override
	public Entreprise modifier(Entreprise entity) throws InvalideOryzException {
		
		Entreprise entreprise =	entrepriseRepository.findById(entity.getId()).get();
		entity.setPassword(entreprise.getPassword());
	       
	  return entrepriseRepository.save(entity);
		}

	@Override
	public List<Entreprise> findAll() {
		List<Entreprise> entrepriseActive = null;
		List<Entreprise> entreprises = entrepriseRepository.findAll();
		entrepriseActive = entreprises.stream()
				.filter(e -> e.isActevated() == true)
				.collect(Collectors.toList());

		return entrepriseActive;
	}

	@Override
	public Entreprise findById(Long id) {
		// TODO Auto-generated method stub
		return entrepriseRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
    entrepriseRepository.deleteById(id);
    return true;
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
