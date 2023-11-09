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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.mgg.siapafismw.model.SalvaFamiliareTracking;
import it.mgg.siapafismw.model.SalvaFamiliareTrackingId;
import it.mgg.siapafismw.model.TipoDocumentoTMiddle;
import it.mgg.siapafismw.repositories.AllegatoRepository;
import it.mgg.siapafismw.repositories.DetenutoRepository;
import it.mgg.siapafismw.repositories.DocumentoTMiddleRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;
import it.mgg.siapafismw.repositories.FamiliareTMiddleRepository;
import it.mgg.siapafismw.repositories.MatricolaTMiddleRepository;
import it.mgg.siapafismw.repositories.RelazioneParentelaTMiddleRepository;
import it.mgg.siapafismw.repositories.SalvaFamiliareTrackingRepository;
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
	
	@Autowired
	private SalvaFamiliareTrackingRepository familiareTrackingRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliareDAOImpl.class);
	
	@Override
	@Transactional
	public void insertFamiliare(FamiliareDTO familiare, String matricola) 
	{
		logger.info("Accesso al DAO per l'inserimento del familiare...");
		
		/* controllo dati del familiare */
		if(StringUtils.isBlank(familiare.getNome()))
		{
			logger.info("Nome del familiare on valido");
			throw new IllegalArgumentException("Nome del familiare non valido");
		}
		
		if(StringUtils.isBlank(familiare.getCognome()))
		{
			logger.info("Cognome del familiare non valido");
			throw new IllegalArgumentException("Cognome del familiare non valido");
		}
		
		if(StringUtils.isBlank(familiare.getCodiceFiscale()))
		{
			logger.info("Codice fiscale del familiare non valido");
			throw new IllegalArgumentException("Codice fiscale del familiare non valido");
		}
		
		/* controllo e riceerca del tipo documennto */
		if(StringUtils.isBlank(familiare.getDocumento()))
		{
			logger.info("Documento del familiare non valido");
			throw new IllegalArgumentException("Documento del familiare non valido");
		}
		
		logger.info("Ricerca tipo documento...");
		Optional<TipoDocumentoTMiddle> optTipoDocumento = this.tipoDocumentoTMiddleRepository
				                       .findByDescrizioneIgnoreCase(familiare.getDocumento());
		
		if(optTipoDocumento.isEmpty())
		{
			logger.info("Impossibile riconoscere il tipo di documento {}",familiare.getDocumento());
			throw new IllegalArgumentException("Impossibile riconoscere il tipo di documento " + 
		                                       familiare.getDocumento());
		}
		
		if(StringUtils.isBlank(familiare.getNumeroDocumento()))
		{
			logger.info("Numero documento del familiare non valido");
			throw new IllegalArgumentException("Numero documento del familiare non valido");
		}
		
		if(StringUtils.isBlank(familiare.getTelefono()) || !familiare.getTelefono().matches("^[0-9]+$"))
		{
			logger.info("Telefono del familiare non valido");
			throw new IllegalArgumentException("Telefono del familiare non valido");
		}
		
		if(StringUtils.isBlank(familiare.getDataDocumento()))
		{
			logger.info("Data documento non valida");
			throw new IllegalArgumentException("Data documento non valida");
		}
		
		/* controllo e ricerca del grado familiare */
		if(StringUtils.isBlank(familiare.getGradoParentela()))
		{
			logger.info("Grado parentela non presente");
			throw new IllegalArgumentException("Grado parentela non presente");
		}
		
		logger.info("Ricerca del grado parentela...");
		Optional<RelazioneParentelaTMiddle> optRelazione = this.parentelaRepository.findByDescrizioneParentela(familiare.getGradoParentela().toUpperCase());
		if(optRelazione.isEmpty())
		{
			logger.info("Impossibile trovare il grado di parentela {}. Si prova ad cercare ua relazione di default...", familiare.getGradoParentela());
			optRelazione = this.parentelaRepository.findByDescrizioneParentela(SiapAfisMWConstants.RELAZIONE_PARENTELA_NON_RILEVATA);
			if(optRelazione.isEmpty())
			{
				logger.info("Impossibile definire il grado di parentela per il familiare specificato");
				throw new IllegalArgumentException("Impossibile definire il grado di parentela per il familiare specificato");
			}
		}
		
		logger.info("Trovata relazione di parentela {}", optRelazione.get().getDescrizioneParentela());
		
		/* controllo di conformita' e conversione della data */
		String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$";
		logger.info("Controllo data con regex {}", dateRegex);
		if(!familiare.getDataDocumento().matches(dateRegex))
		{
			logger.info("La data del documento non e' nel formato dd-MM-yyyy e/o contiene caratteri non validi");
			throw new IllegalArgumentException("La data del documento non e' nel formato dd-MM-yyyy");
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dataDocumento = LocalDate.parse(familiare.getDataDocumento(), formatter);
		
		
		/* controllo presenza familiare con stesso numero di telefono */
//		Optional<Familiare> familiarePresente = familiareRepository.findByTelefono(familiare.getTelefono());
//		if(familiarePresente.isPresent())
//			throw new IllegalArgumentException("Familiare con medesino numero di telefono gia' registrato");
		
		/* controllo detenuto */
		if(StringUtils.isBlank(matricola))
		{
			logger.info("Matricola del detenuto non presente");
			throw new IllegalArgumentException("Matricola del detenuto non presente");
		}
		
		logger.info("Ricerca ID soggetto...");
		Optional<MatricolaTMiddle> idSoggetto = this.matricolaRepository.findById(matricola);
		if(idSoggetto.isEmpty())
		{
			logger.info("Nessun detenuto presente con la matricola specificata");
			throw new IllegalArgumentException("Nessun detenuto presente con la matricola specificata");
		}
		
		/* ricerca massimo progressivo familiare per l'id soggetto trovato */
		logger.info("Ricerca massimo progressivo familiare...");
		Integer maxProgressivoFamiliare = this.familiareTMiddleRepository.getMaxPrgFamiliare(idSoggetto.get().getIdSoggetto());
		logger.info("Prossimo progressivo familiare che verra' utilizzato: {}", maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);
		
		/* inserimento familiare su database TMIDDLE */
		logger.info("Preparazione dell'oggetto FAMILIARE per il salvataggio...");
		FamiliareTMiddle familiareTM = new FamiliareTMiddle();
		familiareTM.setFamiliareId(new FamiliareTMiddleId(idSoggetto.get().getIdSoggetto(), 
				                                          maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1));
		familiareTM.setNome(familiare.getNome());
		familiareTM.setCognome(familiare.getCognome());
		familiareTM.setCodiceFiscale(familiare.getCodiceFiscale());
		familiareTM.setRelazioneParentela(optRelazione.get().getIdParentela());
		familiareTM.setUtenza(familiare.getTelefono());
		
		this.familiareTMiddleRepository.save(familiareTM);
		
		logger.info("Effettuata operazione SAVE del familiare");
		
		/* ricerca progressivo documento */
		logger.info("Ricerca del prossimo progressivo per l'inserimento del documeto...");
		Integer progressivoDocumento = this.documentoTMiddleRepository.getMaxProgressivoDocumento(
				                       idSoggetto.get().getIdSoggetto(), 
				                       maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);	
		
		logger.info("Prossimo progressivo documento che verra' utilizzato: {}", progressivoDocumento != null ? progressivoDocumento + 1 : 1);
		
		logger.info("Preparazione dell'oggetto per il salvataggio del documento...");
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
		
		logger.info("Effettuata operazione SAVE del documento");
		
		/* aggiunta record tracking */
		logger.info("Creazione dell'oggetto per l'inserimento sulla tabella di tracking dell'operazione di salvataggio del familiare...");
		SalvaFamiliareTracking salvaFamiliare = new SalvaFamiliareTracking(
				                                    new SalvaFamiliareTrackingId(idSoggetto.get().getIdSoggetto(), 
				                                    		                     maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1), 
				                                    LocalDateTime.now());
		
		this.familiareTrackingRepository.save(salvaFamiliare);
		
		logger.info("Effettuata operazione SAVE dei dati di tracking");
		
		logger.info("Fine metodo DAO per inserimento familiare");

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
