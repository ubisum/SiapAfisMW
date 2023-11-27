package it.mgg.siapafismw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dao.TrackingDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.utils.ConvertionUtils;

@Service("familiareServiceImpl")
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familiareDAO;
	
	@Autowired
	private TrackingDAO trackingDAO;
	
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
	public FamiliareDTO getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) throws SiapAfisMWException 
	{
		logger.info("Accesso al servizio per la ricerca del familiare...");
		
		if(ricerca == null || (StringUtils.isBlank(ricerca.getCodiceFiscale()) && 
		   StringUtils.isBlank(ricerca.getNumeroTelefono())))
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
		FamiliareDTO familiareDTO = new FamiliareDTO();
		
		/* conversione */
		if(familiare != null)
		{
			logger.info("Trovato familiare. Inizio operazione di coversione dell'entita' in DTO...");
			 familiareDTO = ConvertionUtils.convertFamiliare2DTO(familiare);
			model.setFamiliareModel(familiareDTO);
			
			logger.info("Fine operazione di conversione");
		}
		else
		return null;
		
		/* tracking */
		this.trackingDAO.storeTracking(TrackingOperation.GET_FAMILIARE, ricerca, EsitoTracking.OK);
		
		
		logger.info("Fine del servizio per la ricerca del familiare");
		
		return familiareDTO;
		
	}
	
}
