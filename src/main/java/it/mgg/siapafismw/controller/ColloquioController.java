package it.mgg.siapafismw.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.InsertUpdateDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.service.ColloquioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/GestioneColloqui")
public class ColloquioController 
{
	@Autowired
	@Qualifier("colloquioServiceImpl")
	private ColloquioService colloquioServiceImpl;
	
	@Autowired
	@Qualifier("colloquioServiceMockImpl")
	private ColloquioService colloquioServiceMockImpl;
	
	@Value("${siapafismw.mock}")
	private String mockValue;
	
	private static final Logger logger = LoggerFactory.getLogger(ColloquioController.class);
	
	@PostMapping("/InsertOrUpdateColloquio")
	public ResponseEntity<ColloquioDTO> insertOrUpdateColloquio(@RequestBody InsertUpdateDTO insertUpdate)
	{
		logger.info("Accesso all'endpoint InsertOrUpdateColloquio");
		
		try
		{
			ColloquioDTO colloquio = null;
			
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Richiesta servizio con mock...");
				colloquio = this.colloquioServiceMockImpl.insertUpdateColloquio(insertUpdate);
			}
			
			else
			{
				logger.info("Richiesta servizio senza mock...");
				colloquio = this.colloquioServiceImpl.insertUpdateColloquio(insertUpdate);
			}
			
			return ResponseEntity.ok().body(colloquio);
		}
		
		catch(SiapAfisMWException ex)
		{
			logger.info("Si e' verificata un'eccezione", ex);
			
			return ResponseEntity.status(ex.getStatus()).body(null);
			
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificato un errore interno", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			return ResponseEntity.internalServerError().body(null);
		}
		
	}
}
