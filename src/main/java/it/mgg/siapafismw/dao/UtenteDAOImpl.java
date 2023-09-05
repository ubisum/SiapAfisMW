package it.mgg.siapafismw.dao;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.model.Utente;
import it.mgg.siapafismw.repositories.UtenteRepository;

public class UtenteDAOImpl implements UtenteDAO 
{
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Utente insertUtente(UtenteDTO nuovoUtente) 
	{
		/* controllo validita' dati */
		if(StringUtils.isBlank(nuovoUtente.getCognome()))
			throw new IllegalArgumentException("Cognome non valido");
		
		if(StringUtils.isBlank(nuovoUtente.getEmail()))
			throw new IllegalArgumentException("Email non valida");
		
		if(StringUtils.isBlank(nuovoUtente.getNome()))
			throw new IllegalArgumentException("Nome non valido");
		
		if(StringUtils.isBlank(nuovoUtente.getPassword()))
			throw new IllegalArgumentException("Password non valida");
		
		if(StringUtils.isBlank(nuovoUtente.getUsername()))
			throw new IllegalArgumentException("Username non valido");
		
		/* controllo esistenza di un utente con stesso username */
		Optional<Utente> utenteStessaPw = this.utenteRepository.findById(nuovoUtente.getUsername());
		if(utenteStessaPw.isPresent())
			throw new IllegalArgumentException("Utente gia' presente con lo stesso username");
		
		ModelMapper mapper = new ModelMapper();
		Utente utenteDaSalvare =  mapper.map(nuovoUtente, Utente.class);
		utenteDaSalvare.setPassword(bCryptPasswordEncoder.encode(nuovoUtente.getPassword()));
		
		return utenteRepository.save(utenteDaSalvare);
	}

}
