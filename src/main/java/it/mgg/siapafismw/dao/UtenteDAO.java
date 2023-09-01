package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.model.Utente;

public interface UtenteDAO 
{
	public Utente insertUtente(UtenteDTO nuovoUtente);
}
