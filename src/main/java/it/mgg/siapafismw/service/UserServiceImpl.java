package it.mgg.siapafismw.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.model.Utente;
import it.mgg.siapafismw.repositories.UtenteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UtenteRepository userRepository;
	
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                Utente utente = userRepository.findById(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
                
                ModelMapper mapper = new ModelMapper();
                
                return mapper.map(utente, UtenteDTO.class);
            }
        };
    }

}
