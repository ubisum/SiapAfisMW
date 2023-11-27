package it.mgg.siapafismw.controller;

import javax.print.DocFlavor.STRING;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dao.FamiliareDAOImpl;
import it.mgg.siapafismw.dto.EsitoDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.service.FamiliareService;
import it.mgg.siapafismw.dto.FamiliareDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/familiare")
public class FamiliareController 
{
	@Autowired
	@Qualifier("familiareServiceImpl")
	private FamiliareService familiareService;
	
	@Autowired
	@Qualifier("familiareServiceMockImpl")
	private FamiliareService familiareServiceMock;
	
	@Value("${siapafismw.mock}")
	private String mockValue;
	
	private final String INTERNAL_SERVER_ERROR = "Si e' verificato un errore interno";
	private static final Logger logger = LoggerFactory.getLogger(FamiliareDAOImpl.class);

	
	@PostMapping("/SalvaFamiliare")
	public ResponseEntity<EsitoDTO> salvaFamiliare(@RequestBody FamiliareDTO familiare)
	{
		logger.info("Accesso all'endpoint SalvaFamiliare");
		
		EsitoDTO esito = new EsitoDTO();
		HttpStatus status = null;
		
		try
		{

			String matricola = familiare.getMatricolaDetenutoAssociato();

			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione del servizio mock per l'inserimento del familiare...");
				familiareServiceMock.insertFamiliare(familiare, matricola);
			}
			
			else
			{
				logger.info("Invocazione del servizio per l'inserimento del familiare...");
				familiareService.insertFamiliare(familiare, matricola);
			}
			
			esito.setResponseCode("200");
			esito.setResponseDescription("Familiare aggiunto con successo");
			status = HttpStatus.OK;
			
			logger.info("Preparazione risposta con codice 200...");
		}
		
		catch(SiapAfisMWException ex)
		{
			logger.info("Si e' verificata un'eccezione", ex);
			esito.setResponseCode(ex.getStatus().toString());
			esito.setResponseDescription(ex.getMessage());
			status = ex.getStatus();
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificato un errore interno", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			esito.setResponseCode("500");
			esito.setResponseDescription(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		return ResponseEntity.status(status.value()).body(esito);
	
	}
	
	@GetMapping("/GetFamiliare")
	public ResponseEntity<FamiliareDTO> getFamiliare(@RequestBody SimpleRicercaDTO simpleRicerca)
	{
		logger.info("Accesso all'endpoint GetFamiliare");
		
		try
		{			
			
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione del servizio mock per la ricerca del familiare...");
				return ResponseEntity.ok().body(this.familiareServiceMock.getFamiliareByNumeroTelefonoCodiceFiscale(simpleRicerca));
			}
			
			else
			{
				logger.info("Invocazione del servizio per la ricerca del familiare...");
				return ResponseEntity.ok().body(this.familiareService.getFamiliareByNumeroTelefonoCodiceFiscale(simpleRicerca));
			}
		}
		
		catch(SiapAfisMWException ex)
		{
			logger.info("Si e' verificata un'eccezione", ex);
			
			return ResponseEntity.status(ex.getStatus()).body(null);
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificata un'eccezione interna", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			return ResponseEntity.internalServerError().body(null);
		}	
	}
}
