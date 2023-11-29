package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

public interface ColloquioService 
{
	public void insertUpdateColloquio(ColloquioDTO insertUpdate) throws SiapAfisMWException;
}
