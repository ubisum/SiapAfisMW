package it.mgg.siapafismw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.service.DetenutoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/detenuto")
public class DetenutoController 
{
	@Autowired
	private DetenutoService detenutoService;
	
	@GetMapping("/GetInfoDetenuto/{matricola}")
	public ResponseEntity<DetenutoDTO> getInfoDetenuto(@PathVariable("matricola") String matricola)
	{
		/* invocazione del service per ricerca detenuto */
		DetenutoDTO detenuto = detenutoService.findDetenutoByMatricola(matricola);
		
		if(detenuto != null)
			return ResponseEntity.ok(detenuto);
		
		else
			return ResponseEntity.notFound().build();
	}
	
}
