package it.mgg.siapafismw.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import it.mgg.siapafismw.dto.CofamiliareDTO;
import it.mgg.siapafismw.dto.EsitoDTO;
import it.mgg.siapafismw.service.CofamiliareService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/cofamiliare")
public class CofamiliareController 
{
	@Autowired
	private CofamiliareService cofamiliareService;
	
	private final String INTERNAL_SERVER_ERROR = "Si e' verificato un errore interno";
	
	@PostMapping("/SalvaCoFamiliare")
	private ResponseEntity<EsitoDTO> salvaCoFamiliare(@RequestBody CofamiliareDTO cofamiliare)
	{
		EsitoDTO esito = new EsitoDTO();
		HttpStatus status = null;
		
		try
		{
			cofamiliareService.insertCofamiliare(cofamiliare);
			esito.setResponseCode("200");
			esito.setResponseDescription("Cofamiliare aggiunto con successo");
			
			status = HttpStatus.OK;
		}
		
		catch(Throwable ex)
		{
			esito.setResponseCode("500");
			esito.setResponseDescription(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR);
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return ResponseEntity.status(status).body(esito);
	}
	
	@GetMapping("/GetCofamiliare/{codiceFiscale}")
	private ResponseEntity<CofamiliareDTO> GetCofamiliare(@PathVariable String codiceFiscale)
	{
		
		try
		{
			return ResponseEntity.ok().body(this.cofamiliareService.findCofamiliareByCodiceFiscale(codiceFiscale));
			
		}
		
		catch(Throwable ex)
		{
			return ResponseEntity.internalServerError().body(null);
		}
		
		
	}
}
