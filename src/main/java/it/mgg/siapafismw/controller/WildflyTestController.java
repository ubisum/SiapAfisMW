package it.mgg.siapafismw.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.ListaDetenutiDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/test")
public class WildflyTestController 
{
	@GetMapping("/GetListaDetenuti/{numTelefono}")
	public ResponseEntity<ListaDetenutiDTO> getListaDetenuti(@PathVariable("numTelefono") String numTelefono)
	{
		/* creazione di oggetto fittizio */
		DetenutoDTO detenuto = new DetenutoDTO("Pietro", "Gambadilegno", "AR5695K", "Topolinia", "C");
		
		/* preparazione risposta */
		return ResponseEntity.ok(new ListaDetenutiDTO(List.of(detenuto)));
	}
	
}
