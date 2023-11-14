package it.mgg.siapafismw.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;

@Service("familiareServiceMockImpl")
public class FamiliareServiceMockImpl implements FamiliareService 
{
	private static final Logger logger = LoggerFactory.getLogger(FamiliareServiceMockImpl.class);
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare, String matricola) {
		return;

	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) 
	{
		logger.info("Accesso al servizio mock per la ricerca del familiare...");
		logger.info("Creazione dell'oggeetto da restituire...");
		
		/* MOCK */
		FamiliareModelDTO modelDTO = new FamiliareModelDTO();
		FamiliareDTO familiare = new FamiliareDTO();
		familiare.setCodiceFiscale("MNGTOL097G1Y638F");
		familiare.setCognome("Mattei");
		familiare.setDocumento("Carta d'identita'");
		familiare.setGradoParentela("Padre");
		familiare.setNome("Marco");
		familiare.setNumeroDocumento("DGH09PW3E");
		familiare.setTelefono("06887303798");
		
		AllegatoDTO allegato1 = new AllegatoDTO("patente.jpg", "JPG");
		AllegatoDTO allegato2 = new AllegatoDTO("codice_fiscale.pdf", "PDF");
		
		familiare.setAllegati(new ArrayList<>());
		familiare.getAllegati().add(allegato1);
		familiare.getAllegati().add(allegato2);
		
		modelDTO.setFamiliareModel(familiare);
		
		logger.info("Fine creazione dell'oggetto");
		
		return modelDTO;
	}

}
