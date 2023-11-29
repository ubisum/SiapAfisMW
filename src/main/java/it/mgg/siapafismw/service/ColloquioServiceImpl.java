package it.mgg.siapafismw.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.ColloquioFamiliareTMiddle;
import it.mgg.siapafismw.model.ColloquioTMiddle;
import it.mgg.siapafismw.model.IDDataVerseMapping;
import it.mgg.siapafismw.repositories.ColloquioFamiliareTMIddleRepository;
import it.mgg.siapafismw.repositories.ColloquioTMiddleRepository;
import it.mgg.siapafismw.repositories.IDDataVerseMappingRepository;

@Service("colloquioServiceImpl")
public class ColloquioServiceImpl implements ColloquioService 
{
	@Autowired
	private IDDataVerseMappingRepository idDataVerseMappingRepository;
	
	@Autowired
	private ColloquioFamiliareTMIddleRepository colloquioFamiliareTMIddleRepository;
	
	@Autowired
	private ColloquioTMiddleRepository colloquioTMiddleRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ColloquioServiceImpl.class);
	
	@Override
	public void insertUpdateColloquio(ColloquioDTO insertUpdate) throws SiapAfisMWException 
	{
		logger.info("Inizio procedura di inserimento/aggiornamento del colloquio...");
		
		if(StringUtils.isBlank(insertUpdate.getIdColloquioDataVerse()))
		{
			logger.info("Identificativo Dataverse non presente");
			throw new SiapAfisMWException("Identificativo Dataverse non presente", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Ricerca dati locali sulla base dell'ID colloquio Dataverse...");
		Optional<IDDataVerseMapping> optIdColloquio = this.idDataVerseMappingRepository
				                     .findById(insertUpdate.getIdColloquioDataVerse());
		
		/* variabili di lavoro */
		ColloquioFamiliareTMiddle colloquioFamiliare = null;
		ColloquioTMiddle colloquio = null;
		
		if(optIdColloquio.isPresent())
		{
			logger.info("Trovati dati locali relativamente all'ID {}", insertUpdate.getIdColloquioDataVerse());
			logger.info("Controllo dati associati...");
			
			if(StringUtils.isBlank(optIdColloquio.get().getMatricola()) ||
			  optIdColloquio.get().getPrgColloquio() == null ||
			  optIdColloquio.get().getPrgColloquioFam() == null)
			{
				logger.info("Dati associati all'ID non validi");
				throw new SiapAfisMWException("Dati associati all'ID non validi", 
						                      HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(StringUtils.isBlank(insertUpdate.getMatricola()))
			{
				logger.info("Matricola fornita non valida");
				throw new SiapAfisMWException("Matricola fornita non valida", HttpStatus.BAD_REQUEST);
			}
			
			if(!optIdColloquio.get().getMatricola().equals(insertUpdate.getMatricola()))
			{
				logger.info("La matricola {} fornita non corrisponde a quella associata, "
						+ "sui dati locali, all'ID {}",
						insertUpdate.getMatricola(),
						insertUpdate.getIdColloquioDataVerse());
				
				throw new SiapAfisMWException(String.format("La matricola %s fornita non corrisponde a quella associata, "
						+ "sui dati locali, all'ID %s", 
						insertUpdate.getMatricola(),
						insertUpdate.getIdColloquioDataVerse()), HttpStatus.BAD_REQUEST);
			}
			
			logger.info("Ricerca colloquio...");
			colloquio = this.colloquioTMiddleRepository.findByMatricolaProgressivo(
					               optIdColloquio.get().getMatricola(), 
					               optIdColloquio.get().getPrgColloquio());
			
			if(colloquio == null)
			{
				logger.info("Impossibile trovare il colloquio");
				throw new SiapAfisMWException("Impossibile trovare il colloquio", 
						                      HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			logger.info("Ricerca colloquio familiare...");
			colloquioFamiliare = this.colloquioFamiliareTMIddleRepository.fidByMatricolaProgressivo(
					             optIdColloquio.get().getMatricola(), 
					             optIdColloquio.get().getPrgColloquioFam());
			
		}
		
		else
		{
			
		}
	}

}
