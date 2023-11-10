package it.mgg.siapafismw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.utils.ConvertionUtils;

@Service("familiareServiceImpl")
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familiareDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliareServiceImpl.class);
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare, String matricola) throws SiapAfisMWException 
	{
		logger.info("Accesso al servizio per l'inserimeto del familiare...");
		
		/* inserimento nuovo familiare */
		familiareDAO.insertFamiliare(familiare, matricola);
		
		logger.info("Fine del servizio per l'inserimeto del familiare...");
	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) throws SiapAfisMWException 
	{
		logger.info("Accesso al servizio per la ricerca del familiare...");
		
		if(ricerca == null || (StringUtils.isBlank(ricerca.getCodiceFiscaleFamiliare()) && 
		   StringUtils.isBlank(ricerca.getNumeroTelefonoFamiliare())))
		{
			logger.info("Fornire almeno uno tra codice fiscale e numero di "
					+ " telefono del familiare");
			
			throw new SiapAfisMWException("Fornire almeno uno tra codice fiscale e numero di "
					                         + " telefono del familiare", HttpStatus.BAD_REQUEST);
		}
		
		/* oggetto output */
		FamiliareModelDTO model = new FamiliareModelDTO();
		
		/* ricerca familiare */
		Familiare familiare = familiareDAO.getFamiliareByNumeroTelefonoCodiceFiscale(ricerca);
		
		/* conversione */
		if(familiare != null)
		{
			logger.info("Trovato familiare. Inizio operazione di coversione dell'entita' in DTO...");
			FamiliareDTO familiareDTO = ConvertionUtils.convertFamiliare2DTO(familiare);
			model.setFamiliareModel(familiareDTO);
			
			logger.info("Fine operazione di conversione");
		}
		
		logger.info("Fine del servizio per la ricerca del familiare");
		
		return model;
		
	}
	
}
