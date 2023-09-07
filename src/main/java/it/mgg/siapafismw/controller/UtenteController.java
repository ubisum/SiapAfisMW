package it.mgg.siapafismw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mgg.siapafismw.dto.UtenteDTO;
import it.mgg.siapafismw.service.UtenteService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/utente")
public class UtenteController 
{
	@Autowired
	private UtenteService utenteService;
	
	@PostMapping("/insertUtente")
	public ResponseEntity<UtenteDTO> insertUtente(@RequestBody UtenteDTO utente)
	{
		return ResponseEntity.ok(utenteService.insertUtente(utente));
	}
}
