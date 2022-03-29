package ci.gestion.metier.personne;

import ci.gestion.entites.entreprise.Manager;
import ci.gestion.metier.utilitaire.Imetier;

public interface IManagerMetier extends Imetier<Manager, Long>{
    public boolean matches(String login, String encodedPassword);

}
