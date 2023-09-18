package it.mgg.siapafismw.dao;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.CofamiliareDTO;
import it.mgg.siapafismw.model.Cofamiliare;
import it.mgg.siapafismw.repositories.CofamiliareRepository;

@Component
public class CofamiliareDAOImpl implements CofamiliareDAO 
{
	@Autowired
	private CofamiliareRepository cofamiliareRepository;

	@Override
	public void insertCofamiliare(CofamiliareDTO cofamiliare) 
	{
		/* controllo dati */
		if(cofamiliare == null)
			throw new IllegalArgumentException("Dati cofamiliare non validi");
		
		if(StringUtils.isBlank(cofamiliare.getNome()))
			throw new IllegalArgumentException("Nome cofamiliare non valido");
		
		if(StringUtils.isBlank(cofamiliare.getCognome()))
			throw new IllegalArgumentException("Cognome cofamiliare non valido");
		
		if(StringUtils.isBlank(cofamiliare.getCodiceFiscale()))
			throw new IllegalArgumentException("Codice fiscale cofamiliare non valido");
		
		if(StringUtils.isBlank(cofamiliare.getGradoParentela()))
			throw new IllegalArgumentException("Grado parentela cofamiliare non valido");
		
		Optional<Cofamiliare> optCofamiliare = this.cofamiliareRepository.findById(cofamiliare.getCodiceFiscale());
		if(optCofamiliare.isPresent())
			throw new IllegalArgumentException("Cofamiliare con medeesino codice fiscale gia' presente");
		
		/* mapping */
		ModelMapper mapper = new ModelMapper();
		
		Cofamiliare toDB = mapper.map(cofamiliare, Cofamiliare.class);
		toDB = cofamiliareRepository.save(toDB);
		
		

	}

	@Override
	public Cofamiliare findCofamiliareByCodiceFiscale(String codiceFiscale) 
	{
		if(StringUtils.isBlank(codiceFiscale))
			throw new IllegalArgumentException("Codice fiscale cofamiliare non valido");
		
		Optional<Cofamiliare> optCofamiliare = cofamiliareRepository.findById(codiceFiscale);
		if(optCofamiliare.isEmpty())
			throw new IllegalArgumentException("Nessun cofamiliare presente con il codice fiscale fornito");
		
		return optCofamiliare.get();
	}

}
