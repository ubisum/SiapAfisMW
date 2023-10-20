package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;

public interface FamiliareService 
{
	public void insertFamiliare(FamiliareDTO familiare, String matricola);
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(RicercaDTO ricerca);
}
