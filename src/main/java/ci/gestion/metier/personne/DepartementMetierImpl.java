package ci.gestion.metier.personne;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.DepartementRepository;
import ci.gestion.dao.personne.EntrepriseRepository;
import ci.gestion.entites.personne.Departement;
import ci.gestion.entites.personne.Entreprise;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartementMetierImpl implements IDepartementMetier{
	
	private DepartementRepository departementRepository;
	private EntrepriseRepository entrepriseRepository;
		@Override
		public Departement creer(Departement entity) throws InvalideOryzException {
			
	        Entreprise entreprise = entrepriseRepository.findByNom("GSTORE PLUS SARL").get();
	        System.out.println("Voir entreprise"+entreprise);
	        entity.setEntreprise(entreprise);
			System.out.println("Voir departement"+entity);

			return departementRepository.save(entity);
		}
	  
		@Override
		public Departement modifier(Departement entity) throws InvalideOryzException {
			// TODO Auto-generated method stub
			
			 Entreprise entreprise = entrepriseRepository.findByNom("GSTORE PLUS SARL").get();
		        System.out.println("Voir entreprise:"+entreprise);
		        entity.setEntreprise(entreprise);
		        System.out.println("Voir departement modif:"+entity);
			return departementRepository.save(entity);
		}

		@Override
		public List<Departement> findAll() {
			// TODO Auto-generated method stub
			return departementRepository.findAll();
		}

		@Override
		public Departement findById(Long id) {
			// TODO Auto-generated method stub
			return departementRepository.findById(id).get();
		}

		@Override
		public boolean supprimer(Long id) {
	    departementRepository.deleteById(id);
			return true;
		}

		@Override
		public boolean supprimer(List<Departement> entites) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean existe(Long id) {
			// TODO Auto-generated method stub
			return false;
		}

		


}
