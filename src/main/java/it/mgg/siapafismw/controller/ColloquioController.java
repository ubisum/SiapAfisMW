package it.mgg.siapafismw.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.EsitoDTO;
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
	public ResponseEntity<EsitoDTO> insertOrUpdateColloquio(@RequestBody ColloquioDTO insertUpdate)
	{
		logger.info("Accesso all'endpoint InsertOrUpdateColloquio");
		EsitoDTO esito = new EsitoDTO();
		
		try
		{
			
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Richiesta servizio con mock...");
				this.colloquioServiceMockImpl.insertUpdateColloquio(insertUpdate);
			}
			
			else
			{
				logger.info("Richiesta servizio senza mock...");
				this.colloquioServiceImpl.insertUpdateColloquio(insertUpdate);
			}
			
			esito.setResponseCode(String.valueOf(HttpStatus.OK.value()));
			esito.setResponseDescription("Colloquio aggiunto o modificato con successo");
			
			return ResponseEntity.ok().body(esito);
		}
		
		catch(SiapAfisMWException ex)
		{
			logger.info("Si e' verificata un'eccezione", ex);
			
			esito.setResponseCode(String.valueOf(ex.getStatus().value()));
			esito.setResponseDescription(ex.getMessage());
			
			return ResponseEntity.status(ex.getStatus()).body(esito);
			
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificato un errore interno", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			esito.setResponseCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			esito.setResponseDescription("Si e' verificato un errore interno");
			
			return ResponseEntity.internalServerError().body(esito);
		}
		
	}
}
