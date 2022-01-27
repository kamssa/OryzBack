package ci.gestion.metier.personne;

import java.util.Optional;

import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.metier.Imetier;





public interface IRoleMetier  extends Imetier<Role, Long>{
	Optional<Role> findByName(RoleName roleName);

}
