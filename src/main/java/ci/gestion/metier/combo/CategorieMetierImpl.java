package ci.gestion.metier.combo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.CategorieRepository;
import ci.gestion.dao.MaterielRepository;
import ci.gestion.entites.stock.Categorie;
import ci.gestion.entites.stock.Materiaux;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategorieMetierImpl implements CategorieMetier{
 CategorieRepository categorieRepository;
 private MaterielRepository materielRepository;

	@Override
	public Categorie creer(Categorie entity) throws InvalideOryzException {
		System.out.println("Voir entre:"+ entity);
		Categorie cat = null;
			
			  if ((entity.getLibelle().equals(null)) || (entity.getLibelle() == "")) {
			  throw new InvalideOryzException("Le libelle ne peut etre null"); }
			 
		
		//Optional<Categorie> cat = null;
		 List<Categorie> cats = categorieRepository.getCategorieByIdEntreprise(entity.getIdEntreprise());
		 if(cats.isEmpty()) {
			 cat =	categorieRepository.save(entity);
		 }else {
			 for (Categorie categorie : cats) {
					if (entity.getLibelle().equals(categorie.getLibelle())) {
						throw new InvalideOryzException("Ce libelle est deja utilise");
					}else {
					 cat =	categorieRepository.save(entity);
					}
				}
		 }
		
		/*
		 * cat = categorieRepository.findByLibelle(entity.getLibelle()); if
		 * (cat.isPresent()) { throw new
		 * InvalideOryzException("Ce libelle est deja utilise"); }
		 */
		return cat;
	}

	@Override
	public Categorie modifier(Categorie entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return categorieRepository.save(entity);
	}

	@Override
	public List<Categorie> findAll() {
		// TODO Auto-generated method stub
		return categorieRepository.findAll();
	}

	@Override
	public Categorie findById(Long id) {
		// TODO Auto-generated method stub
		return categorieRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		categorieRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Categorie> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Categorie> getCategorieByIdEntreprise(long id) {
		
		return categorieRepository.getCategorieByIdEntreprise(id);
	}

	@Override
	public Optional<Categorie> findByLibelle(String libelle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Materiaux> listMatParEntreprise(long id) {
		
		List<Materiaux> mats = new ArrayList<>(); 
		List<Categorie> cats = categorieRepository.getCategorieByIdEntreprise(id);
		if(!cats.isEmpty()) {
			for (Categorie categorie : cats) {
				List<Materiaux> materiaux = materielRepository.getMaterielByIdCategorie(categorie.getId());
				 for (Materiaux categorie2 : materiaux) {
					 mats.add(categorie2);
				}
					System.out.println("Voir retour mat:"+ mats);
				
		}	
		}else {
			System.out.println("categorei vide:");
		}
		
			return mats;
		
		
	}

}
