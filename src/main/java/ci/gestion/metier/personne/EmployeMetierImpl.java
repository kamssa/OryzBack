package ci.gestion.metier.personne;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.DepartementRepository;
import ci.gestion.dao.personne.EmployeRepository;
import ci.gestion.dao.personne.RoleRepository;
import ci.gestion.entites.entreprise.Departement;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.entites.stock.Categorie;
import ci.gestion.entites.stock.Materiaux;
import ci.gestion.metier.exception.InvalideOryzException;



@Service
public class EmployeMetierImpl implements IEmployeMetier{
@Autowired
private EmployeRepository employeRepository;
@Autowired
private RoleRepository rolRepository;
@Autowired
private DepartementRepository departementRepository;
@Autowired
private RoleRepository roleRepository;
@Autowired
PasswordEncoder passwordEncoder;

	@Override
	public Employe creer(Employe entity) throws InvalideOryzException {
		System.out.println("personne a enregistrer" + ":" + entity);
		if ((entity.getEmail().equals(null)) || (entity.getEmail() == "")) {
			throw new InvalideOryzException("Le email ne peut etre null");
		}
		
		Optional<Employe> pers = null; 

		pers = employeRepository.findByEmail(entity.getEmail());
		if (pers.isPresent()) {
			throw new InvalideOryzException("Ce mail est deja utilise");
		}

      	entity.setPassword(passwordEncoder.encode(entity.getPassword()));
      	
		
		return employeRepository.save(entity);
	}

	@Override
	public Employe modifier(Employe modif) throws InvalideOryzException {
		 String nomComplet = modif.getNom() + " " + modif.getPrenom();
		 modif.setNomComplet(nomComplet);
		 Employe empl = employeRepository.findById(modif.getId()).get();
	     modif.setPassword(empl.getPassword());
         Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYE).get();
         modif.setRoles(Collections.singleton(userRole));
         return employeRepository.save(modif);
}

	@Override
	public List<Employe> findAll() {
		// TODO Auto-generated method stub
		return employeRepository.findAll();
	}

	@Override
	public Employe findById(Long id) {
		// TODO Auto-generated method stub
		return employeRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		employeRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Employe> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public List<Employe> getDepByIdEntreprise(Long id) {
		
		return employeRepository.getEmployeByIdEntreprise(id);
	}
	@Override
	public List<Employe> listEmployeParEntreprise(long id) {
		
		List<Employe> employes = new ArrayList<>(); 
		List<Departement> departements = departementRepository.getDepByIdEntreprise(id);
		if(!departements.isEmpty()) {
			for (Departement dep : departements) {
				List<Employe> empl = employeRepository.getEmployeByIdDepartement(dep.getId());
				 for (Employe employes2 : empl) {
					 employes.add(employes2);
				}
					System.out.println("Voir retour mat:"+ employes);
				
		}	
		}else {
			System.out.println("employe vide:");
		}
		
			return employes;
		
		
	}

	@Override
	public Employe addRoleToEmploye(Long empl, Long role) {
		Employe emp = employeRepository.findById(empl).get();
        Role userRole = roleRepository.findById(role).get();
        Set<Role> roles = new HashSet<>();
        roles = emp.getRoles();
        roles.add(userRole);
        emp.setRoles(roles);
        return employeRepository.save(emp);
	}

	
	
}
