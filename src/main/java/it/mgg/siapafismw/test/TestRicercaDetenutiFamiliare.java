package it.mgg.siapafismw.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import it.mgg.siapafismw.controller.DetenutoController;
import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;

@SpringBootApplication
public class TestRicercaDetenutiFamiliare 
{
	@Autowired
	public static DetenutoController detenutoController;
	
	public static void main(String[] args) 
	{
		RicercaDTO ricerca = new RicercaDTO();
		
		ResponseEntity<List<DetenutoDTO>> listaDetenuti = detenutoController.getListaDetenuti(ricerca);
		
		System.out.println("Fine");

	}

}
