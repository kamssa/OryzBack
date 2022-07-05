package ci.gestion.metier.client;

import java.util.List;

import ci.gestion.entites.versement.Versement;
import ci.gestion.metier.utilitaire.Imetier;

public interface VersementMetier extends Imetier<Versement, Long>{
	Versement findVersementByIdTravaux(long id);


}
