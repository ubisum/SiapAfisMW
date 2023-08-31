package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.model.Detenuto;

public interface DetenutoDAO 
{
	public Detenuto findDetenutoByMatricola(String matricola);
}
