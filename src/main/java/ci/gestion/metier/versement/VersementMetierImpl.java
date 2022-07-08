package ci.gestion.metier.versement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ci.gestion.dao.TravauxRepository;
import ci.gestion.dao.VersementRepository;
import ci.gestion.dao.detail.DetailVersementRepository;
import ci.gestion.entites.site.Travaux;
import ci.gestion.entites.versement.DetailVersement;
import ci.gestion.entites.versement.Versement;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VersementMetierImpl implements VersementMetier{
	private DetailVersementRepository detailVersementRepository;

	private VersementRepository versementRepository;
	private TravauxRepository travauxRepository;
	@Override
	public Versement creer(Versement entity) throws InvalideOryzException {

		Versement vers = null;
		double solde = 0;
		double montantVerse = 0;
		double reste = 0;
		Optional<Versement> versement = versementRepository.findVersementByIdTravaux(entity.getTravaux().getId());
		if(versement.isPresent()) {
			List<DetailVersement> detailVersements = entity.getDetailVersement();
			for (DetailVersement detail : detailVersements) {
			      montantVerse = detail.getMontantVerse();
					 solde = versement.get().getSolde();
					 solde += montantVerse;
					
					 Travaux tr = travauxRepository.findById(entity.getTravaux().getId()).get();
						reste = tr.getBudget() - solde;
						versement.get().setSolde(solde);  
						versement.get().setReste(reste);
						//DetailVersement dv = detailVersementRepository.save(detail);
						
					   vers = versementRepository.save(versement.get());
					   detail.setIdVersement(vers.getId());
						detailVersementRepository.save(detail);
							

					}
			 
			
		}else {

			List<DetailVersement> detailVersements = entity.getDetailVersement();
			for (DetailVersement detail : detailVersements) {
				System.out.println("Voir  le detail:" +detail);

				     montantVerse = detail.getMontantVerse();
					 solde = entity.getSolde();
					 solde = montantVerse;

					 Travaux tr = travauxRepository.findById(entity.getTravaux().getId()).get();
						reste = tr.getBudget() - solde;
						entity.setSolde(solde);
						entity.setReste(reste);
						DetailVersement dv = detailVersementRepository.save(detail);
					vers = versementRepository.save(entity);
					detail.setIdVersement(vers.getId());
					detailVersementRepository.save(dv);
                   
}
			    

		}
		
		return vers;
		
	}

	@Override
	public Versement modifier(Versement entity) throws InvalideOryzException {
		// TODO Auto-generated method stub
		return  versementRepository.save(entity);
	}

	@Override
	public List<Versement> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Versement findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Versement> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Versement findVersementByIdTravaux(long id) {
		// TODO Auto-generated method stub
		return versementRepository.findVersementByIdTravaux(id).get();
	}

}