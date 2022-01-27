package ci.gestion.metier.personne;

import java.util.Optional;

import ci.gestion.entites.shared.Personne;
import ci.gestion.metier.Imetier;



public interface IPersonneMetier extends Imetier<Personne, Long>{
	Optional<Personne> findByEmail(String login);
	Optional<Personne> findByEmailOrTelephone(String email, String telephone);
}
