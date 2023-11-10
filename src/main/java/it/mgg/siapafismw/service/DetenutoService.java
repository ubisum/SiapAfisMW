package it.mgg.siapafismw.service;

import java.util.List;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

public interface DetenutoService 
{
	public DetenutoDTO findDetenutoByMatricola(String matricola) throws SiapAfisMWException;
	public List<DetenutoDTO> getAllDetenuti();
	public List<DetenutoDTO> getDetenutiByNumeroTelefono(String numeroTelefono) throws SiapAfisMWException;
	public SlotDisponibileDTO getSlotDisponibili(String matricola) throws SiapAfisMWException;
	public List<DetenutoDTO> findDetenutiByCFNumeroTelefono(RicercaDTO ricerca) throws SiapAfisMWException;
}
