package ci.gestion.metier.personne;

import java.util.List;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.shared.Role;
import ci.gestion.metier.utilitaire.Imetier;



public interface IEmployeMetier extends Imetier<Employe, Long>{
	List<Employe> getDepByIdEntreprise(Long id);
	public List<Employe> listEmployeParEntreprise(long id);
	Employe addRoleToEmploye(Long empl, Long role);
}
