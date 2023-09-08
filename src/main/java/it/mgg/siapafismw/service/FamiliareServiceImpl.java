package it.mgg.siapafismw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;

@Service
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familaireDAO;
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare) 
	{
		/* inserimento nuovo familiare */
		familaireDAO.insertFamiliare(familiare);
	}
	
}
