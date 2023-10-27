package it.mgg.siapafismw.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.model.Allegato;
import it.mgg.siapafismw.model.Detenuto;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.repositories.AllegatoRepository;
import it.mgg.siapafismw.repositories.DetenutoRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;
import jakarta.transaction.Transactional;

@Component
public class FamiliareDAOImpl implements FamiliareDAO 
{
	@Autowired
	private FamiliareRepository familiareRepository;
	
	@Autowired
	private AllegatoRepository allegatoRepository;
	
	@Autowired
	private DetenutoRepository detenutoRepository;
	
	@Override
	@Transactional
	public void insertFamiliare(FamiliareDTO familiare, String matricola) 
	{
		/* controllo dati del familiare */
		if(StringUtils.isBlank(familiare.getNome()))
			throw new IllegalArgumentException("Nome del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getCognome()))
			throw new IllegalArgumentException("Cognome del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getCodiceFiscale()))
			throw new IllegalArgumentException("Codice fiscale del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getDocumento()))
			throw new IllegalArgumentException("Documento del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getNumeroDocumento()))
			throw new IllegalArgumentException("Numero documento del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getTelefono()) || !familiare.getTelefono().matches("^[0-9]+$"))
			throw new IllegalArgumentException("Telefono del familiare non valido");
		
		/* controllo presenza familiare con stesso numero di telefono */
		Optional<Familiare> familiarePresente = familiareRepository.findByTelefono(familiare.getTelefono());
		if(familiarePresente.isPresent())
			throw new IllegalArgumentException("Familiare con medesino numero di telefono gia' registrato");
		
		/* controllo detenuto */
		if(StringUtils.isBlank(matricola))
			throw new IllegalArgumentException("Matricola del detenuto non presente");
		
		Optional<Detenuto> optDetenuto = this.detenutoRepository.findById(matricola);
		if(optDetenuto.isEmpty())
			throw new IllegalArgumentException("Nessun detenuto presente con la matricola fornita");
		
		/* salvataggio familiare */
		ModelMapper mapper = new ModelMapper();
		Familiare toDB = mapper.map(familiare, Familiare.class);
		toDB.setListaAllegati(null);
		toDB.setListaDetenuti(Arrays.asList(optDetenuto.get()));
		
		Familiare familiareSalvato = familiareRepository.save(toDB);
		
		/* controllo allegati */
		if(CollectionUtils.isNotEmpty(familiare.getListaAllegati()))
		{
			List<String> listaNomi = new ArrayList<>();
			for(AllegatoDTO allegato : familiare.getListaAllegati())
			{
				if(StringUtils.isNotBlank(allegato.getNomeFile()))
					listaNomi.add(allegato.getNomeFile());
				
				else
					throw new IllegalArgumentException("Il nome di uno degli allegati non e' presente");
			}
			
			List<Allegato> allegatiPresenti = allegatoRepository.findByNomeFileIn(listaNomi);
			if(CollectionUtils.isNotEmpty(allegatiPresenti))
				throw new IllegalArgumentException("Uno o piu' allegati risultano registrati coi nomi forniti");
			
			/* aggiunta allegati */
			for(AllegatoDTO allegato : familiare.getListaAllegati())
			{
				Allegato allegatoDaSalvare = mapper.map(allegato, Allegato.class);
				allegatoDaSalvare.setFamiliare(familiareSalvato);
				
				allegatoRepository.save(allegatoDaSalvare);
			}
		}
		
		/* aggiornamento detenuto */
		if(CollectionUtils.isEmpty(optDetenuto.get().getFamiliari()))
			optDetenuto.get().setFamiliari(Arrays.asList(familiareSalvato));
		
		else
			optDetenuto.get().getFamiliari().add(familiareSalvato);
		
		this.detenutoRepository.save(optDetenuto.get());
	}

	@Override
	public Familiare getFamiliareByNumeroTelefono(String numeroTelefono) 
	{
		/* controllo numero telefono */
		if(StringUtils.isBlank(numeroTelefono))
			throw new IllegalArgumentException("Telefono del familiare non valido");
		
		/* ricerca familiare */
		Optional<Familiare> optFamiliare = this.familiareRepository.findById(numeroTelefono);
		if(optFamiliare.isEmpty())
			throw new IllegalArgumentException("Nessun familiare con il telefono fornito");
		
		return optFamiliare.get();
	}

	@Override
	public Familiare getFamiliareByCodiceFiscale(String codiceFiscale) 
	{
		/* controllo numero telefono */
		if(StringUtils.isBlank(codiceFiscale))
			throw new IllegalArgumentException("Codice fiscale del familiare non valido");
		
		/* ricerca familiare */
		Optional<Familiare> optFamiliare = this.familiareRepository.findByCodiceFiscale(codiceFiscale);
		if(optFamiliare.isEmpty())
			throw new IllegalArgumentException("Nessun familiare con il codice fiscale fornito");
		
		return optFamiliare.get();
		
	}

}