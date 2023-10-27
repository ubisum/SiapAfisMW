package it.mgg.siapafismw.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;

@Service("familiareServiceMockImpl")
public class FamiliareServiceMockImpl implements FamiliareService {

	@Override
	public void insertFamiliare(FamiliareDTO familiare, String matricola) {
		return;

	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(RicercaDTO ricerca) {
		
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
		
		familiare.setListaAllegati(new ArrayList<>());
		familiare.getListaAllegati().add(allegato1);
		familiare.getListaAllegati().add(allegato2);
		
		modelDTO.setFamiliareModel(familiare);
		
		return modelDTO;
	}

}
