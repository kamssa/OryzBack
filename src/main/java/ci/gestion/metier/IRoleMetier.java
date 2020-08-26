package ci.gestion.metier;

import java.util.Optional;

import ci.gestion.entites.Role;
import ci.gestion.entites.RoleName;

public interface IRoleMetier  extends Imetier<Role, Long>{
	Optional<Role> findByName(RoleName roleName);

}
