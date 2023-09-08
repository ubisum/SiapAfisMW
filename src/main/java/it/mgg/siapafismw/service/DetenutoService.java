package it.mgg.siapafismw.service;

import java.util.List;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;

public interface DetenutoService 
{
	public DetenutoDTO findDetenutoByMatricola(String matricola);
	public List<DetenutoDTO> getAllDetenuti();
	public List<DetenutoDTO> getDetenutiByNumeroTelefono(String numeroTelefono);
	public SlotDisponibileDTO getSlotDisponibili(String matricola);

}
