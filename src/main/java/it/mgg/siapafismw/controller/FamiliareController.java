package it.mgg.siapafismw.controller;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
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
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.service.FamiliareModelDTO;
import it.mgg.siapafismw.service.FamiliareService;

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

	
	@PostMapping("/SalvaFamiliare/{matricola}")
	public ResponseEntity<EsitoDTO> salvaFamiliare(@RequestBody FamiliareModelDTO familiare, 
			                                       @PathVariable String matricola)
	{
		logger.info("Accesso all'endpoint SalvaFamiliare");
		
		EsitoDTO esito = new EsitoDTO();
		HttpStatus status = null;
		
		try
		{
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione del servizio mock per l'inserimento del familiare...");
				familiareServiceMock.insertFamiliare(familiare.getFamiliareModel(), matricola);
			}
			
			else
			{
				logger.info("Invocazione del servizio per l'inserimento del familiare...");
				familiareService.insertFamiliare(familiare.getFamiliareModel(), matricola);
			}
			
			esito.setResponseCode("200");
			esito.setResponseDescription("Familiare aggiunto con successo");
			status = HttpStatus.OK;
			
			logger.info("Preparazione risposta con codice 200...");
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificato un errore interno", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			ex.printStackTrace();
			esito.setResponseCode("500");
			esito.setResponseDescription(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		return ResponseEntity.status(status.value()).body(esito);
	
	}
	
	@GetMapping("/GetFamiliare")
	public ResponseEntity<FamiliareModelDTO> getFamiliare(@RequestBody SimpleRicercaDTO simpleRicerca)
	{
		logger.info("Accesso all'endpoint GetFamiliare");
		
		try
		{
			logger.info("Mapping delle informazioni in input");
			ModelMapper mapper = new ModelMapper();
			RicercaDTO ricerca = mapper.map(simpleRicerca, RicercaDTO.class);
			
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione del servizio mock per la ricerca del familiare...");
				return ResponseEntity.ok().body(this.familiareServiceMock.getFamiliareByNumeroTelefonoCodiceFiscale(ricerca));
			}
			
			else
			{
				logger.info("Invocazione del servizio per la ricerca del familiare...");
				return ResponseEntity.ok().body(this.familiareService.getFamiliareByNumeroTelefonoCodiceFiscale(ricerca));
			}
		}
		
		catch(Throwable ex)
		{
			logger.info("Si e' verificata un'eccezione interna", ex);
			logger.info("Preparazione risposta con codice 500...");
			
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body(null);
		}	
	}
}
