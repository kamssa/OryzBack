package ci.gestion.metier.personne;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.EmployeRepository;
import ci.gestion.entites.personne.Employe;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeMetierImpl implements IEmployeMetier{
	@Autowired
	private EmployeRepository employeRepository;

		@Override
		public Employe creer(Employe entity) throws InvalideOryzException {
			
			return employeRepository.save(entity);
		}

		@Override
		public Employe modifier(Employe entity) throws InvalideOryzException {
			return employeRepository.save(entity);
		}

		@Override
		public List<Employe> findAll() {
			return employeRepository.findAll();
		}

		@Override
		public Employe findById(Long id) {
			return employeRepository.findById(id).get();
		}

		@Override
		public boolean supprimer(Long id) {
			return false;
		}

		@Override
		public boolean supprimer(List<Employe> entites) {
			return false;
		}

		@Override
		public boolean existe(Long id) {
			return false;
		}

}
