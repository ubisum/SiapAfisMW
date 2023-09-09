package it.mgg.siapafismw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.CofamiliareDAO;
import it.mgg.siapafismw.dto.CofamiliareDTO;

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

}
