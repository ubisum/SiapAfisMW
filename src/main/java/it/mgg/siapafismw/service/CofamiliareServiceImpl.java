package it.mgg.siapafismw.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.CofamiliareDAO;
import it.mgg.siapafismw.dto.CofamiliareDTO;
import it.mgg.siapafismw.model.Cofamiliare;

@Service
public class CofamiliareServiceImpl implements CofamiliareService 
{
	@Autowired
	private CofamiliareDAO cofamiliareDAO;
	
	@Override
	public void insertCofamiliare(CofamiliareDTO cofamiliare) 
	{
		cofamiliareDAO.insertCofamiliare(cofamiliare);

	}

	@Override
	public CofamiliareDTO findCofamiliareByCodiceFiscale(String codiceFiscale) 
	{
		Cofamiliare input = this.cofamiliareDAO.findCofamiliareByCodiceFiscale(codiceFiscale);
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(input, CofamiliareDTO.class);
	}

}
