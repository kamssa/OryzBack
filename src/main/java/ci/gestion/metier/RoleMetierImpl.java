package ci.gestion.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.RoleRepository;
import ci.gestion.entites.Role;
import ci.gestion.entites.RoleName;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class RoleMetierImpl implements IRoleMetier{
@Autowired
private RoleRepository roleRepository;
	@Override
	public Role creer(Role entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role modifier(Role entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Role> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<Role> findByName(RoleName roleName) {
		// TODO Auto-generated method stub
		return roleRepository.findByName(roleName);
	}

}
