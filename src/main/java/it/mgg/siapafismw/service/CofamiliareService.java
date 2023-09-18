package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.CofamiliareDTO;

public interface CofamiliareService 
{
	public void insertCofamiliare(CofamiliareDTO cofamiliare);
	public CofamiliareDTO findCofamiliareByCodiceFiscale(String codiceFiscale);
}
