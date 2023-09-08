package it.mgg.siapafismw.dao;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.model.Detenuto;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.repositories.DetenutoRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;

@Component
public class DetenutoDAOImpl implements DetenutoDAO 
{
	@Autowired
	private DetenutoRepository detenutoRepository;
	
	@Autowired
	private FamiliareRepository familiareRepository;
	
	@Override
	public Detenuto findDetenutoByMatricola(String matricola) 
	{
		/* controllo presenza matricola */
		if(StringUtils.isBlank(matricola))
			throw new IllegalArgumentException("Il valore della matricola fornito non e' valido");
		
		Optional<Detenuto> detenuto = detenutoRepository.findById(matricola);
		if(detenuto.isEmpty())
			return null;
		
		else
			return detenuto.get();
	}

	@Override
	public List<Detenuto> getAllDetenuti() 
	{
		/* ricerca di tutti i detenuti presenti sul database */
		return detenutoRepository.findAll();
	}

	@Override
	public List<Detenuto> getDetenutiByNumeroTelefono(String numeroTelefono) 
	{
		/* controlli input */
		if(StringUtils.isBlank(numeroTelefono))
			throw new IllegalArgumentException("Il numero di telefono fornito non e' valido");
		
		/* ricerca detenuto */
		Optional<Familiare> familiare = familiareRepository.findById(numeroTelefono);
		if(familiare.isEmpty())
			throw new IllegalArgumentException("Nessun familiare trovato con il numero di telefono specificato");
		
		return familiare.get().getListaDetenuti();
	}

}
