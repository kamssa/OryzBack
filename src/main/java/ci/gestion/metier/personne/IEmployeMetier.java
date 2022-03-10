package ci.gestion.metier.personne;

import java.util.List;

import ci.gestion.entites.entreprise.Employe;
import ci.gestion.metier.Imetier;



public interface IEmployeMetier extends Imetier<Employe, Long>{
	List<Employe> getDepByIdEntreprise(Long id);
	public List<Employe> listEmployeParEntreprise(long id);
}
