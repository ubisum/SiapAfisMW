package it.mgg.siapafismw.service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.InsertUpdateDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

public interface ColloquioService 
{
	public ColloquioDTO insertUpdateColloquio(InsertUpdateDTO insertUpdate) throws SiapAfisMWException;
}
