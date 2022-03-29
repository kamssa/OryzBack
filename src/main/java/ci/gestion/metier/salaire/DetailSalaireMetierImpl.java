package ci.gestion.metier.salaire;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.DetailSalaireRepository;
import ci.gestion.dao.MontantVerseSalaireRepository;
import ci.gestion.dao.personne.EmployeRepository;
import ci.gestion.entites.entreprise.Employe;
import ci.gestion.entites.salaire.DetailSalaire;
import ci.gestion.entites.salaire.VersementSalaire;
import ci.gestion.metier.exception.InvalideOryzException;

@Service
public class DetailSalaireMetierImpl implements IDetailSalaireMetier {
	@Autowired
	private DetailSalaireRepository salaireRepository;
	@Autowired
	private EmployeRepository employeRepository;
	@Autowired
	private MontantVerseSalaireRepository montantVerseSalaireRepository;

	@Override
	public DetailSalaire creer(DetailSalaire entity) throws InvalideOryzException {
		double salaire = 0;
		double montantVerse = 0;
		double reste = 0;
		double montant = 0;
		double somme = 0;
		double versement = 0;
		VersementSalaire  versementSalaire1 = null;
		
		
		VersementSalaire versementSalaire = new VersementSalaire();
		versementSalaire.setMontantVerse(entity.getMontantVerse());
		versementSalaire1 = montantVerseSalaireRepository.save(versementSalaire);
        List<VersementSalaire>  versementSalaires = montantVerseSalaireRepository.findAll();
		for(VersementSalaire salaireVerse: versementSalaires ) {
			versement += salaireVerse.getMontantVerse();
			}
		montantVerseSalaireRepository.deleteAll();
		versementSalaire1.setMontantVerse(versement);
		DetailSalaire detailSalaire = salaireRepository.save(entity);
		versementSalaire1.setEmployeId(detailSalaire.getEmployeId());
		VersementSalaire verses = montantVerseSalaireRepository.save(versementSalaire1);
		Employe employe = employeRepository.findById(detailSalaire.getEmployeId()).get();
		salaire = employe.getSalaire().getMontant();
		reste = salaire - verses.getMontantVerse();
		detailSalaire.setMontantVerse(reste);
		salaireRepository.save(detailSalaire);
		if (salaire == verses.getMontantVerse()){
			throw new InvalideOryzException("Paiement du salaire terminé");
		}
		return entity;
	}

	@Override
	public DetailSalaire modifier(DetailSalaire entity) throws InvalideOryzException {
		return salaireRepository.save(entity);
	}

	@Override
	public List<DetailSalaire> findAll() {
		// TODO Auto-generated method stub
		return salaireRepository.findAll();
	}

	@Override
	public DetailSalaire findById(Long id) {
		return salaireRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<DetailSalaire> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
