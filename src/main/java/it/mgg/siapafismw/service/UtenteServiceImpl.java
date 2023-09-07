package it.mgg.siapafismw.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.UtenteDAO;
import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.model.Utente;

@Service
public class UtenteServiceImpl implements UtenteService 
{
	@Autowired
	private UtenteDAO utenteDAO;
		
	@Override
	public UtenteDTO insertUtente(UtenteDTO nuovoUtente) 
	{
		/* inizio servizio inserimento */
		Utente utente = utenteDAO.insertUtente(nuovoUtente);
		
		ModelMapper mapper = new ModelMapper();
		return mapper.map(utente, UtenteDTO.class);
	}

}
