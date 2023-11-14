package it.mgg.siapafismw.service;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.InsertUpdateDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

@Service("colloquioServiceMockImpl")
public class ColloquioServiceMockImpl implements ColloquioService 
{
	private static final Logger logger = LoggerFactory.getLogger(ColloquioServiceMockImpl.class);
	
	@Override
	public ColloquioDTO insertUpdateColloquio(InsertUpdateDTO insertUpdate) throws SiapAfisMWException
	{
		logger.info("Preparazione oggetto con mock...");
		
		ColloquioDTO colloquio = new ColloquioDTO();
		colloquio.setMatricola(RandomStringUtils.random(11, true, true).toUpperCase());
		colloquio.setIdColloquioDataVerse(RandomStringUtils.random(20, true, true).toUpperCase());
		colloquio.setData(LocalDateTime.now().toString());
		colloquio.setStato("R");
		colloquio.setTipo("O");
		colloquio.setModalita("V");
		colloquio.setOreRichieste(2);
		colloquio.setOreEffettive(2);
		colloquio.setOraInizioColloquio("12:00");
		colloquio.setOraFineColloquio("14:00");
		colloquio.setCodiceFiscaleFamiliare1(RandomStringUtils.random(16, true, true).toUpperCase());
		colloquio.setNumeroTelefonoFamiliare1(RandomStringUtils.random(10, false, true));
		
		return colloquio;
	}

}
