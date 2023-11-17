package it.mgg.siapafismw.controller;

import java.util.List;
import java.util.stream.Collectors;

import it.mgg.siapafismw.utils.ConvertionUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.GetListaDetenutiDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.RicercaDetenutoDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.service.DetenutoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/detenuto")
public class DetenutoController 
{
	@Autowired
	@Qualifier("detenutoServiceImpl")
	private DetenutoService detenutoService;
	
	@Autowired
	@Qualifier("detenutoServiceMockImpl")
	private DetenutoService detenutoServiceMock;
	
	@Value("${siapafismw.mock}")
	private String mockValue;
	
	private static final Logger logger = LoggerFactory.getLogger(DetenutoController.class);
	
	@GetMapping("/GetInfoDetenuto")
	public ResponseEntity<DetenutoDTO> getInfoDetenuto(@RequestBody RicercaDetenutoDTO ricerca)
	{
		logger.info("Accesso all'endpoint GetInfoDetenuto");
		
		/* invocazione del service per ricerca detenuto */
		DetenutoDTO detenuto = null;
		
		try
		{
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione del servizio mock per la ricerca delle informazioni del detenuto...");
				detenuto = detenutoServiceMock.findDetenutoByMatricola(ricerca.getMatricola());
			}
			
			
			else
			{
				logger.info("Invocazione del servizio per la ricerca delle informazioni del detenuto...");
				detenuto = detenutoService.findDetenutoByMatricola(ricerca.getMatricola());
			}
			
			if(detenuto != null)
			{
				logger.info("Detenuto trovato, preparazione risposta con codice {}", HttpStatus.OK.value());
				return ResponseEntity.ok(detenuto);
			}
			
			else
			{
				logger.info("Detenuto non trovato, preparazione risposta con codice {}", HttpStatus.NOT_FOUND.value());
				return ResponseEntity.notFound().build();
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
	
	@GetMapping("/GetListaDetenuti")
	public ResponseEntity<List<GetListaDetenutiDTO>> getListaDetenuti(@RequestBody SimpleRicercaDTO simpleRicerca)
	{
		logger.info("Accesso all'endpoint GetListaDetenuti");
		
		try
		{
			/* ricerca dei detenuti */
			List<DetenutoDTO> listaDetenuti = null;
			
			logger.info("Mapping dei dati in ingresso...");
			ModelMapper mapper = new ModelMapper();
			RicercaDTO ricerca = ConvertionUtils.convertSimpleRicercaDTOtoRicercaDTO(simpleRicerca);

			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			{
				logger.info("Invocazione servizio mock per ricerca lista detenuti di un familiare...");
				listaDetenuti = this.detenutoServiceMock.findDetenutiByCFNumeroTelefono(ricerca);
			}
			
			else
			{
				logger.info("Invocazione servizio per ricerca lista detenuti di un familiare...");
				listaDetenuti = this.detenutoService.findDetenutiByCFNumeroTelefono(ricerca);
			}
			
			return ResponseEntity.ok(listaDetenuti.stream()
				   .map(det -> mapper.map(det, GetListaDetenutiDTO.class)).collect(Collectors.toList()));
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
	
//	@GetMapping("/GetSlotDisponibili/{matricola}")
//	public ResponseEntity<SlotDisponibileDTO> getSlotDisponibili(@PathVariable String matricola)
//	{
//		SlotDisponibileDTO slot = null;
//		HttpStatus status = null;
//		
//		try
//		{
//			slot = detenutoService.getSlotDisponibili(matricola);
//			slot.setResponseCode("200");
//			slot.setRespondeDescription("Ricerca slot conclusa con successo");
//			
//			status = HttpStatus.OK;
//		}
//		
//		catch(Throwable ex)
//		{
//			ex.printStackTrace();
//			
//			slot = new SlotDisponibileDTO();
//			slot.setResponseCode("500");
//			slot.setRespondeDescription(StringUtils.isNotBlank(ex.getMessage()) ? 
//					ex.getMessage() : INTERNAL_SERVER_ERROR);
//			
//			status = HttpStatus.INTERNAL_SERVER_ERROR;
//		}
//		
//		return ResponseEntity.status(status).body(slot);
//		
//	}
}
