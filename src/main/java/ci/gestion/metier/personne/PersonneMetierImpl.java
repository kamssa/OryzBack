package ci.gestion.metier.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.PersonneReposiory;
import ci.gestion.entites.shared.Personne;
import ci.gestion.metier.exception.InvalideOryzException;



@Service
public class PersonneMetierImpl implements IPersonneMetier{
	@Autowired
	private PersonneReposiory personneReposiory;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public Personne creer(Personne p) throws InvalideOryzException {
		System.out.println("personne a enregistrer" + ":" + p);
		if ((p.getEmail().equals(null)) || (p.getEmail() == "")) {
			throw new InvalideOryzException("Le email ne peut etre null");
		}
		
		Optional<Personne> pers = null;

		pers = personneReposiory.findByEmail(p.getEmail());
		if (pers.isPresent()) {
			throw new InvalideOryzException("Ce mail est deja utilise");
		}

	p.setPassword(passwordEncoder.encode(p.getPassword()));
		
		return personneReposiory.save(p);
	
	}

	@Override
	public Personne modifier(Personne modif) throws InvalideOryzException {
		Personne pers =personneReposiory.findById(modif.getId()).get();
		modif.setPassword(pers.getPassword());
		return personneReposiory.save(modif);

	}

	@Override
	public List<Personne> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Personne findById(Long id) {
		// TODO Auto-generated method stub
		return personneReposiory.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		personneReposiory.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Personne> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public Optional<Personne> findByEmail(String login) {
		// TODO Auto-generated method stub
		return personneReposiory.findByEmail(login);
	}

	@Override
	public Optional<Personne> findByEmailOrTelephone(String email, String telephone) {
		// TODO Auto-generated method stub
		return personneReposiory.findByEmailOrTelephone(email, telephone);
	}

}
