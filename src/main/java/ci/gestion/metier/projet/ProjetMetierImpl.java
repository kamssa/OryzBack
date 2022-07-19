package ci.gestion.metier.projet;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.ProjetRepository;
import ci.gestion.dao.entreprise.EntrepriseRepository;
import ci.gestion.entites.site.Projet;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProjetMetierImpl implements ProjetMetier{
private ProjetRepository projetRepository;
	@Override
	public Projet creer(Projet entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return projetRepository.save(entity);
	}

	@Override
	public Projet modifier(Projet entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return projetRepository.save(entity);
	}

	@Override
	public List<Projet> findAll() {
		// TODO Auto-generated method stub
		return projetRepository.findAll();
	}

	@Override
	public Projet findById(Long id) {
		// TODO Auto-generated method stub
		return projetRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		projetRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Projet> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Projet> chercherProjetParMc(String mc, String nom) {
		// TODO Auto-generated method stub
		return projetRepository.chercherProjetParMc(mc, nom);
	}

	@Override
	public List<Projet> findProjetBIdEntreprise(long id) {
		// TODO Auto-generated method stub
		return projetRepository.findProjetByIdEntreprise(id);
	}

	@Override
	public List<Projet> findProjetByIdClient(long id) {
		// TODO Auto-generated method stub
		return projetRepository.findProjetByIdClient(id);
	}

}
