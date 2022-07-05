package ci.gestion.metier.client;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ci.gestion.dao.personne.ClientRepository;
import ci.gestion.entites.shared.Role;
import ci.gestion.entites.shared.RoleName;
import ci.gestion.entites.site.Client;
import ci.gestion.metier.exception.InvalideOryzException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientMetierImpl implements ClientMetier{
private ClientRepository clientRepository;
private PasswordEncoder passwordEncoder;
	@Override
	public Client creer(Client entity) throws InvalideOryzException {
	return clientRepository.save(entity);
	}

	@Override
	public Client modifier(Client entity) throws InvalideOryzException {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));

	return clientRepository.save(entity);
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return clientRepository.findAll();
	}

	@Override
	public Client findById(Long id) {
		// TODO Auto-generated method stub
		return clientRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Client> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
