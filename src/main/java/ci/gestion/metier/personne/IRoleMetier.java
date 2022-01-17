package ci.gestion.metier.personne;

import java.util.Optional;

import ci.gestion.entites.personne.Role;
import ci.gestion.entites.personne.RoleName;
import ci.gestion.metier.Imetier;




public interface IRoleMetier  extends Imetier<Role, Long>{
	Optional<Role> findByName(RoleName roleName);

}
