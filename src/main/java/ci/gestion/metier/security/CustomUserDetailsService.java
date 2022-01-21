package ci.gestion.metier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ci.gestion.dao.personne.PersonneRepository;
import ci.gestion.entites.personne.Personne;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	PersonneRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Personne user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouve : " + email));

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		
		 
		Personne user = userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

		return UserPrincipal.create(user);
	}
}