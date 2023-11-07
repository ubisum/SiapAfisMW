package it.mgg.siapafismw.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import it.mgg.siapafismw.model.DocumentoTMiddle;
import it.mgg.siapafismw.model.DocumentoTMiddleId;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.model.FamiliareTMiddle;
import it.mgg.siapafismw.model.FamiliareTMiddleId;
import it.mgg.siapafismw.model.MatricolaTMiddle;
import it.mgg.siapafismw.model.RelazioneParentelaTMiddle;
import it.mgg.siapafismw.model.TipoDocumentoTMiddle;
import it.mgg.siapafismw.repositories.AllegatoRepository;
import it.mgg.siapafismw.repositories.DetenutoRepository;
import it.mgg.siapafismw.repositories.DocumentoTMiddleRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;
import it.mgg.siapafismw.repositories.FamiliareTMiddleRepository;
import it.mgg.siapafismw.repositories.MatricolaTMiddleRepository;
import it.mgg.siapafismw.repositories.RelazioneParentelaTMiddleRepository;
import it.mgg.siapafismw.repositories.TipoDocumentoTMiddleRepository;
import it.mgg.siapafismw.utils.SiapAfisMWConstants;
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
	
	@Autowired
	private MatricolaTMiddleRepository matricolaRepository;
	
	@Autowired
	private FamiliareTMiddleRepository familiareTMiddleRepository;
	
	@Autowired
	private RelazioneParentelaTMiddleRepository parentelaRepository;
	
	@Autowired
	private DocumentoTMiddleRepository documentoTMiddleRepository;
	
	@Autowired
	private TipoDocumentoTMiddleRepository tipoDocumentoTMiddleRepository;
	
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
		
		/* controllo e riceerca del tipo documennto */
		if(StringUtils.isBlank(familiare.getDocumento()))
			throw new IllegalArgumentException("Documento del familiare non valido");
		
		Optional<TipoDocumentoTMiddle> optTipoDocumento = this.tipoDocumentoTMiddleRepository
				                       .findByDescrizioneIgnoreCase(familiare.getDocumento());
		
		if(optTipoDocumento.isEmpty())
			throw new IllegalArgumentException("Impossibilee riconoscere il tipo di documento " + 
		                                       familiare.getDocumento());
		
		if(StringUtils.isBlank(familiare.getNumeroDocumento()))
			throw new IllegalArgumentException("Numero documento del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getTelefono()) || !familiare.getTelefono().matches("^[0-9]+$"))
			throw new IllegalArgumentException("Telefono del familiare non valido");
		
		if(StringUtils.isBlank(familiare.getDataDocumento()))
			throw new IllegalArgumentException("Data documento non valida");
		
		/* controllo e ricerca del grado familiare */
		if(StringUtils.isBlank(familiare.getGradoParentela()))
			throw new IllegalArgumentException("Grado parentela non presente");
		
		Optional<RelazioneParentelaTMiddle> optRelazione = this.parentelaRepository.findByDescrizioneParentela(familiare.getGradoParentela().toUpperCase());
		if(optRelazione.isEmpty())
		{
			optRelazione = this.parentelaRepository.findByDescrizioneParentela(SiapAfisMWConstants.RELAZIONE_PARENTELA_NON_RILEVATA);
			if(optRelazione.isEmpty())
				throw new IllegalArgumentException("Impossibile definire il grado di parentela per il familiare specificato");
		}
		
		/* controllo di conformita' e conversione della data */
		String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$";
		if(!familiare.getDataDocumento().matches(dateRegex))
			throw new IllegalArgumentException("La data del documento non e' nel formato dd-MM-yyyy");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dataDocumento = LocalDate.parse(familiare.getDataDocumento(), formatter);
		
		
		/* controllo presenza familiare con stesso numero di telefono */
		Optional<Familiare> familiarePresente = familiareRepository.findByTelefono(familiare.getTelefono());
		if(familiarePresente.isPresent())
			throw new IllegalArgumentException("Familiare con medesino numero di telefono gia' registrato");
		
		/* controllo detenuto */
		if(StringUtils.isBlank(matricola))
			throw new IllegalArgumentException("Matricola del detenuto non presente");
		
		Optional<MatricolaTMiddle> idSoggetto = this.matricolaRepository.findById(matricola);
		if(idSoggetto.isEmpty())
			throw new IllegalArgumentException("Nessun detenuto presente con la matricola specificata");
		
		/* ricerca massimo progressivo familiare per l'id soggetto trovato */
		Integer maxProgressivoFamiliare = this.familiareTMiddleRepository.getMaxPrgFamiliare(idSoggetto.get().getIdSoggetto());
		
		/* inserimento familiare su database TMIDDLE */
		FamiliareTMiddle familiareTM = new FamiliareTMiddle();
		familiareTM.setFamiliareId(new FamiliareTMiddleId(idSoggetto.get().getIdSoggetto(), 
				                                          maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1));
		familiareTM.setNome(familiare.getNome());
		familiareTM.setCognome(familiare.getCognome());
		familiareTM.setCodiceFiscale(familiare.getCodiceFiscale());
		familiareTM.setRelazioneParentela(optRelazione.get().getIdParentela());
		familiareTM.setUtenza(familiare.getTelefono());
		
		this.familiareTMiddleRepository.save(familiareTM);
		
		/* ricerca progressivo documento */
		Integer progressivoDocumento = this.documentoTMiddleRepository.getMaxProgressivoDocumento(
				                       idSoggetto.get().getIdSoggetto(), 
				                       maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);		
		
		DocumentoTMiddle documentoTM = new DocumentoTMiddle();
		documentoTM.setDocumentoId(new DocumentoTMiddleId(idSoggetto.get().getIdSoggetto(),
				                                          maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1,
				                                          progressivoDocumento != null ? progressivoDocumento + 1 : 1, 
				                                          optTipoDocumento.get().getIdTipoDocumento(), 
				                                          familiare.getNumeroDocumento(), 
				                                          dataDocumento));
		
		documentoTM.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
		documentoTM.setDataIns(LocalDateTime.now());
		
		this.documentoTMiddleRepository.save(documentoTM);
		
//		salvaFamiliareLocalmente(familiare, matricola);
	}

	private void salvaFamiliareLocalmente(FamiliareDTO familiare, String matricola) {
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
