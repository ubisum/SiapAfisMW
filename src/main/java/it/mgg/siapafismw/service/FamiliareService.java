package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

public interface FamiliareService 
{
	public void insertFamiliare(FamiliareDTO familiare, String matricola) throws SiapAfisMWException;
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) throws SiapAfisMWException;
}
