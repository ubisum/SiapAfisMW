package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.DetenutoDTO;

public interface DetenutoService 
{
	public DetenutoDTO findDetenutoByMatricola(String matricola);
}
