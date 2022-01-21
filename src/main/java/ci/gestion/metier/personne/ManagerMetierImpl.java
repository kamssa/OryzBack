package ci.gestion.metier.personne;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.ManagerRepository;
import ci.gestion.dao.personne.PersonneRepository;
import ci.gestion.dao.personne.RoleRepository;
import ci.gestion.entites.entreprise.Manager;
import ci.gestion.entites.personne.Personne;
import ci.gestion.entites.personne.Role;
import ci.gestion.entites.personne.RoleName;
import ci.gestion.metier.exception.InvalideOryzException;


@Service
public class ManagerMetierImpl implements IManagerMetier { 
@Autowired
private ManagerRepository managerRepository; 
@Autowired
private PersonneRepository personneReposiory; 
@Autowired
private RoleRepository roleRepository;
@Autowired
PasswordEncoder passwordEncoder;
	@Override
	public Manager creer(Manager p) throws InvalideOryzException {
		System.out.println("personne a enregistrer" + ":" + p);
		if ((p.getEmail().equals(null)) || (p.getEmail() == "")) {
			throw new  InvalideOryzException("Le email ne peut etre null");
		}
		
        p.setPassword(passwordEncoder.encode(p.getPassword()));
		
		return managerRepository.save(p);
	}

	@Override
	public Manager modifier(Manager modif) throws InvalideOryzException {
		 String nomComplet = modif.getNom() + " " + modif.getPrenom();
		 modif.setNomComplet(nomComplet); 
		 modif.setPassword(passwordEncoder.encode(modif.getPassword()));
         Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER).get();
         modif.setRoles(Collections.singleton(userRole));
         return managerRepository.save(modif);
	}

	@Override
	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	@Override
	public Manager findById(Long id) {
		return managerRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		managerRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Manager> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	
	// verifier les passwords encode
		@Override
		public boolean matches(String login, String oldPassword) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			Manager manage = managerRepository.getManageByEmail(login);
			return encoder.matches(oldPassword, manage.getPassword());
		}

		

	
}
