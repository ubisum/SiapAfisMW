package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.dto.CofamiliareDTO;
import it.mgg.siapafismw.model.Cofamiliare;

public interface CofamiliareDAO 
{
	public void insertCofamiliare(CofamiliareDTO cofamiliare);
	public Cofamiliare findCofamiliareByCodiceFiscale(String codiceFiscale);
}
