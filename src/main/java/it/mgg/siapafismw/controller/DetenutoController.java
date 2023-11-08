package it.mgg.siapafismw.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.RicercaDetenutoDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;
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
	
	private final String INTERNAL_SERVER_ERROR = "Si e' verificato un errore interno";
	
	@GetMapping("/GetInfoDetenuto")
	public ResponseEntity<DetenutoDTO> getInfoDetenuto(@RequestBody RicercaDetenutoDTO ricerca)
	{
		/* invocazione del service per ricerca detenuto */
		DetenutoDTO detenuto = null;
		if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
			detenuto = detenutoServiceMock.findDetenutoByMatricola(ricerca.getMatricola());
		
		
		else
			detenuto = detenutoService.findDetenutoByMatricola(ricerca.getMatricola());
		
		if(detenuto != null)
			return ResponseEntity.ok(detenuto);
		
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/GetListaDetenuti")
	public ResponseEntity<List<DetenutoDTO>> getListaDetenuti(@RequestBody SimpleRicercaDTO simpleicerca)
	{
		try
		{
			/* ricerca dei detenuti */
			List<DetenutoDTO> listaDetenuti = null;
			ModelMapper mapper = new ModelMapper();
			RicercaDTO ricerca = mapper.map(simpleicerca, RicercaDTO.class);
			
			if(StringUtils.isNotBlank(this.mockValue) && "true".equalsIgnoreCase(this.mockValue))
				listaDetenuti = this.detenutoServiceMock.findDetenutiByCFNumeroTelefono(ricerca);
			
			else
				listaDetenuti = this.detenutoService.findDetenutiByCFNumeroTelefono(ricerca);
			
			return ResponseEntity.ok(listaDetenuti);
		}
		
		catch(Throwable ex)
		{
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body(null);
		}
	}
	
	@GetMapping("/GetSlotDisponibili/{matricola}")
	public ResponseEntity<SlotDisponibileDTO> getSlotDisponibili(@PathVariable String matricola)
	{
		SlotDisponibileDTO slot = null;
		HttpStatus status = null;
		
		try
		{
			slot = detenutoService.getSlotDisponibili(matricola);
			slot.setResponseCode("200");
			slot.setRespondeDescription("Ricerca slot conclusa con successo");
			
			status = HttpStatus.OK;
		}
		
		catch(Throwable ex)
		{
			ex.printStackTrace();
			
			slot = new SlotDisponibileDTO();
			slot.setResponseCode("500");
			slot.setRespondeDescription(StringUtils.isNotBlank(ex.getMessage()) ? 
					ex.getMessage() : INTERNAL_SERVER_ERROR);
			
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return ResponseEntity.status(status).body(slot);
		
	}
}
