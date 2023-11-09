package it.mgg.siapafismw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.utils.ConvertionUtils;

@Service("familiareServiceImpl")
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familiareDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliareServiceImpl.class);
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare, String matricola) 
	{
		logger.info("Accesso al servizio per l'inserimeto del familiare...");
		
		/* inserimento nuovo familiare */
		familiareDAO.insertFamiliare(familiare, matricola);
		
		logger.info("Fine del servizio per l'inserimeto del familiare...");
	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(RicercaDTO ricerca) 
	{
		logger.info("Accesso al servizio per la ricerca del familiare...");
		
		if(ricerca == null || (StringUtils.isBlank(ricerca.getCodiceFiscaleFamiliare()) && 
		   StringUtils.isBlank(ricerca.getNumeroTelefonoFamiliare())))
		{
			logger.info("Fornire almeno uno tra codice fiscale e numero di "
					+ " telefono del familiare");
			
			throw new IllegalArgumentException("Fornire almeno uno tra codice fiscale e numero di "
					                         + " telefono del familiare");
		}
		
		/* oggetto output */
		FamiliareModelDTO model = new FamiliareModelDTO();
		
		/* ricerca familiare */
		Familiare familiare = null;
		
		if(StringUtils.isNotBlank(ricerca.getCodiceFiscaleFamiliare()))
		{
			logger.info("Rilevata preseza del codice fiscale {}. "
					+ "Si utilizza questa informazione peer la ricerca del familiare...", ricerca.getCodiceFiscaleFamiliare());
			familiare = this.familiareDAO.getFamiliareByCodiceFiscale(ricerca.getCodiceFiscaleFamiliare());
		}
		
		else
		{
			logger.info("Codice fiscale non trovato. Si utilizza il numero di telefono {} "
					+ "per effettuare la ricerca", ricerca.getNumeroTelefonoFamiliare());
			familiare = this.familiareDAO.getFamiliareByNumeroTelefono(ricerca.getNumeroTelefonoFamiliare());
		}
		
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
