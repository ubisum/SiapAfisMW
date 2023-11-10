package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.Familiare;

public interface FamiliareDAO 
{
	public void insertFamiliare(FamiliareDTO familiare, String matricola) throws SiapAfisMWException ;
	public Familiare getFamiliareByNumeroTelefono(String numeroTelefono) throws SiapAfisMWException;
	public Familiare getFamiliareByCodiceFiscale(String codiceFiscale) throws SiapAfisMWException;
	public Familiare getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) throws SiapAfisMWException;
}
