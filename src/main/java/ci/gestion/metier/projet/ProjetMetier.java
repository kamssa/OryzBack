package ci.gestion.metier.projet;

import java.util.List;

import ci.gestion.entites.site.Projet;
import ci.gestion.metier.utilitaire.Imetier;

public interface ProjetMetier extends Imetier<Projet, Long>{
	List<Projet> chercherProjetParMc(String mc, String nom);
	List<Projet> findProjetBIdEntreprise(long id );
	List<Projet> findProjetByIdClient(long id);

}
