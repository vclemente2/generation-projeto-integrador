package br.com.generation.comerciocomcausa.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.generation.comerciocomcausa.model.User;
import br.com.generation.comerciocomcausa.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


		Optional<User> user = userRepository.findByEmail(userName);

		if (user.isPresent())
			return new UserDetailsImpl(user.get());
		else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	
	}
}
