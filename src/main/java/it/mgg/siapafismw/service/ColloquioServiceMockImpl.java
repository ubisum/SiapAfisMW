package it.mgg.siapafismw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

@Service("colloquioServiceMockImpl")
public class ColloquioServiceMockImpl implements ColloquioService 
{
	private static final Logger logger = LoggerFactory.getLogger(ColloquioServiceMockImpl.class);
	
	@Override
	public void insertUpdateColloquio(ColloquioDTO insertUpdate) throws SiapAfisMWException
	{
		logger.info("Invocato metodo per inserimento colloquio con mock...");
		
	}

}
