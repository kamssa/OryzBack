package ci.gestion.metier.personne;

import java.util.List;

import org.springframework.data.repository.query.Param;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.metier.utilitaire.Imetier;



public interface IEmployeMetier extends Imetier<Employe, Long>{
	List<Employe> getDepByIdEntreprise(Long id);
	public List<Employe> listEmployeParEntreprise(long id);
	Employe addRoleToEmploye(Long empl, Long role);
	List<Employe> chercherEmployeParMc(@Param("nom") String nomEmploye, String nom);
}
