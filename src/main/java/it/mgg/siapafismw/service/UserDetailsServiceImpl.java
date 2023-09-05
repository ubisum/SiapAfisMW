package it.mgg.siapafismw.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.model.Utente;
import it.mgg.siapafismw.repositories.UtenteRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService 
{
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		/* controllo validita' dello username */
		if(StringUtils.isBlank(username))
			throw new UsernameNotFoundException("Username non valido");
		
		/* ricerca utente sulla base dello username */
		Optional<Utente> optUtente = utenteRepository.findById(username);
		if(optUtente.isEmpty())
			throw new UsernameNotFoundException("Nessun utente trovato con lo username " + username);
		
		/* riempimento oggetto di risposta */
		return null;
	}

}
