package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.FamiliareDTO;

public interface FamiliareService 
{
	public void insertFamiliare(FamiliareDTO familiare, String matricola);
	public FamiliareModelDTO getFamiliareByNumeroTelefono(String numeroTelefono);
}
