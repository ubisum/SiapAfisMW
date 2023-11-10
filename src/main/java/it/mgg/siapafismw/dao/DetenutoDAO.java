package it.mgg.siapafismw.dao;

import java.util.List;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.Detenuto;

public interface DetenutoDAO 
{
	public DetenutoDTO findDetenutoByMatricola(String matricola) throws SiapAfisMWException;
	public List<Detenuto> getAllDetenuti();
	public List<Detenuto> getDetenutiByNumeroTelefono(String numeroTelefono) throws SiapAfisMWException;
	public List<Detenuto> findDetenutiByCFNumeroTelefono(RicercaDTO ricerca) throws SiapAfisMWException;
	public Integer findIdSoggettoFromMatricola(String matricola) throws SiapAfisMWException;
}
