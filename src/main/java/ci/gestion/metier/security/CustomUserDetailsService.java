package ci.gestion.metier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.personne.PersonneReposiory;
import ci.gestion.entites.shared.Personne;




@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	PersonneReposiory userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String emailOrTelephone) throws UsernameNotFoundException {
		
		Personne user = userRepository.findByEmailOrTelephone(emailOrTelephone, emailOrTelephone)
				.orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouve : " + emailOrTelephone));

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		
		 
		Personne user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return UserPrincipal.create(user);
	}
}