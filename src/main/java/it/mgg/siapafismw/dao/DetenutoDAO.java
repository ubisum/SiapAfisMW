package it.mgg.siapafismw.dao;

import java.util.List;

import it.mgg.siapafismw.model.Detenuto;

public interface DetenutoDAO 
{
	public Detenuto findDetenutoByMatricola(String matricola);
	public List<Detenuto> getAllDetenuti();
}
