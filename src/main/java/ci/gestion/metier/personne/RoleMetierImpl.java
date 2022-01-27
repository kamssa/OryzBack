package ci.gestion.metier.personne;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.RoleRepository;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;






@Service
public class RoleMetierImpl implements IRoleMetier{
@Autowired
private RoleRepository roleRepository;

@Override
public Role creer(Role entity) {
	// TODO Auto-generated method stub
	return roleRepository.save(entity);
}

@Override
public Role modifier(Role entity) {
	// TODO Auto-generated method stub
	return roleRepository.save(entity);
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
