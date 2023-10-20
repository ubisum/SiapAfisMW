package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.model.Familiare;

public interface FamiliareDAO 
{
	public void insertFamiliare(FamiliareDTO familiare, String matricola);
	public Familiare getFamiliareByNumeroTelefono(String numeroTelefono);
	public Familiare getFamiliareByCodiceFiscale(String codiceFiscale);
}
