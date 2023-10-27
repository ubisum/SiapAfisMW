package it.mgg.siapafismw.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.FamiliareDAO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.utils.ConvertionUtils;

@Service("familiareServiceImpl")
public class FamiliareServiceImpl implements FamiliareService
{
	@Autowired
	private FamiliareDAO familiareDAO;
	
	@Override
	public void insertFamiliare(FamiliareDTO familiare, String matricola) 
	{
		/* inserimento nuovo familiare */
		familiareDAO.insertFamiliare(familiare, matricola);
	}

	@Override
	public FamiliareModelDTO getFamiliareByNumeroTelefonoCodiceFiscale(RicercaDTO ricerca) 
	{
		if(ricerca == null || (StringUtils.isBlank(ricerca.getCodiceFiscaleFamiliare()) && 
		   StringUtils.isBlank(ricerca.getNumeroTelefonoFamiliare())))
			
			throw new IllegalArgumentException("Fornire almeno uno tra codice fiscale e numero di "
					                         + " telefono del familiare");
		
		/* oggetto output */
		FamiliareModelDTO model = new FamiliareModelDTO();
		
		/* ricerca familiare */
		Familiare familiare = null;
		
		if(StringUtils.isNotBlank(ricerca.getCodiceFiscaleFamiliare()))
			familiare = this.familiareDAO.getFamiliareByCodiceFiscale(ricerca.getCodiceFiscaleFamiliare());
		
		else
			familiare = this.familiareDAO.getFamiliareByNumeroTelefono(ricerca.getNumeroTelefonoFamiliare());
		
		/* conversione */
		if(familiare != null)
		{
			FamiliareDTO familiareDTO = ConvertionUtils.convertFamiliare2DTO(familiare);
			model.setFamiliareModel(familiareDTO);
		}
		
		return model;
		
	}
	
}
