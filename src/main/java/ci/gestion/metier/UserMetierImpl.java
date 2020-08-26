package ci.gestion.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.UserRepository;
import ci.gestion.entites.User;
import ci.gestion.metier.exception.InvalideOryzException;



@Service
public class UserMetierImpl implements IUserMetier{
@Autowired
private UserRepository userepository;
@Autowired
private PasswordEncoder passwordEncode;
	@Override
	public User creer(User p) throws InvalideOryzException {
		if ((p.getEmail().equals(null)) || (p.getEmail() == "")) {
			throw new InvalideOryzException("Le login ne peut etre null");
		}
		
    Optional<User> pers = null;

		pers = userepository.findByEmail(p.getEmail());
		if (pers.isPresent()) {
			throw new InvalideOryzException("Ce login est deja utilise");
		}
		
		p.setPassword(passwordEncode.encode(p.getPassword()));
		return userepository.save(p);
	}

	@Override
	public User modifier(User entity) throws InvalideOryzException {
		return userepository.save(entity);
	}

	@Override
	public List<User> findAll() {
		return null;
	}

	@Override
	public User findById(Long id) {
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		return false;
	}

	@Override
	public boolean supprimer(List<User> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return null;
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userepository.existsByEmail(email);
	}

}
