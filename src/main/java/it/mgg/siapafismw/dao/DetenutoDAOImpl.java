package it.mgg.siapafismw.dao;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.model.Detenuto;
import it.mgg.siapafismw.repositories.DetenutoRepository;

@Component
public class DetenutoDAOImpl implements DetenutoDAO 
{
	@Autowired
	private DetenutoRepository detenutoRepository;
	
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

}
