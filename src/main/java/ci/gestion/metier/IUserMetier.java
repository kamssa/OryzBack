package ci.gestion.metier;

import java.util.Optional;

import ci.gestion.entites.User;

public interface IUserMetier extends Imetier<User, Long>{
	Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
