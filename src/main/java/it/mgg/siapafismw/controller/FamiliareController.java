package it.mgg.siapafismw.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.EsitoDTO;
import it.mgg.siapafismw.service.FamiliareModelDTO;
import it.mgg.siapafismw.service.FamiliareService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/familiare")
public class FamiliareController 
{
	@Autowired
	private FamiliareService familiareService;
	
	private final String INTERNAL_SERVER_ERROR = "Si e' verificato un errore interno";
	
	@PostMapping("/SalvaFamiliare")
	public ResponseEntity<EsitoDTO> salvaFamiliare(@RequestBody FamiliareModelDTO familiare)
	{
		EsitoDTO esito = new EsitoDTO();
		HttpStatus status = null;
		
		try
		{
			familiareService.insertFamiliare(familiare.getFamiliareModel());
			esito.setResponseCode("200");
			esito.setResponseDescription("Familiare aggiunto con successo");
			status = HttpStatus.OK;
		}
		
		catch(Throwable ex)
		{
			esito.setResponseCode("500");
			esito.setResponseDescription(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : INTERNAL_SERVER_ERROR);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		return ResponseEntity.status(status.value()).body(esito);
	
	}
}
