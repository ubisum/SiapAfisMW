package it.mgg.siapafismw.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.model.Familiare;

@Service
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familiareDAO;
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare) 
	{
		/* inserimento nuovo familiare */
		familiareDAO.insertFamiliare(familiare);
	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefono(String numeroTelefono) 
	{
		/* oggetto output */
		FamiliareModelDTO model = new FamiliareModelDTO();
		
		/* ricerca familiare */
		Familiare familiare = this.familiareDAO.getFamiliareByNumeroTelefono(numeroTelefono);
		
		ModelMapper mapper = new ModelMapper();
		model.setFamiliareModel(mapper.map(familiare, FamiliareDTO.class));
		
		return model;
		
	}
	
}
