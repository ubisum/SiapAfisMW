package it.mgg.siapafismw.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.AutorizzazioneFamiliareTMiddle;
import it.mgg.siapafismw.model.AutorizzazioneFamiliareTMiddleId;
import it.mgg.siapafismw.model.AutorizzazioneTMiddle;
import it.mgg.siapafismw.model.AutorizzazioneTMiddleId;
import it.mgg.siapafismw.model.DocumentoTMiddle;
import it.mgg.siapafismw.model.DocumentoTMiddleId;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.model.FamiliareTMiddle;
import it.mgg.siapafismw.model.FamiliareTMiddleId;
import it.mgg.siapafismw.model.MatricolaTMiddle;
import it.mgg.siapafismw.model.RelazioneParentelaTMiddle;
import it.mgg.siapafismw.model.TipoDocumentoTMiddle;
import it.mgg.siapafismw.repositories.AutorizzazioneFamiliareTMiddleRepository;
import it.mgg.siapafismw.repositories.AutorizzazioneTMiddleRepository;
import it.mgg.siapafismw.repositories.DocumentoTMiddleRepository;
import it.mgg.siapafismw.repositories.FamiliareRepository;
import it.mgg.siapafismw.repositories.FamiliareTMiddleRepository;
import it.mgg.siapafismw.repositories.MatricolaTMiddleRepository;
import it.mgg.siapafismw.repositories.RelazioneParentelaTMiddleRepository;
import it.mgg.siapafismw.repositories.TipoDocumentoTMiddleRepository;
import it.mgg.siapafismw.utils.SiapAfisMWConstants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Component
public class FamiliareDAOImpl implements FamiliareDAO 
{
	@Autowired
	private FamiliareRepository familiareRepository;
	
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
	private AutorizzazioneTMiddleRepository autorizzazioneTMiddleRepository;
	
	@Autowired
	private AutorizzazioneFamiliareTMiddleRepository autorizzazioneFamiliareTMiddleRepository;
	
	@Autowired
	private TrackingDAOImpl trackingDAOImpl;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(FamiliareDAOImpl.class);
	
	@Override
	@Transactional
	public void insertFamiliare(FamiliareDTO familiare, String matricola) throws SiapAfisMWException 
	{
		logger.info("Accesso al DAO per l'inserimento del familiare...");
		
		/* controllo dati del familiare */
		if(StringUtils.isBlank(familiare.getNome()))
		{
			logger.info("Nome del familiare on valido");
			throw new SiapAfisMWException("Nome del familiare non valido", HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isBlank(familiare.getCognome()))
		{
			logger.info("Cognome del familiare non valido");
			throw new SiapAfisMWException("Cognome del familiare non valido", HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isAllBlank(familiare.getCodiceFiscale(), familiare.getTelefono()))
		{
			logger.info("E' necessario fornire almeno uno tra codice fiscale e numero di telefono per il familiare da inserire");
			throw new SiapAfisMWException("E' necessario fornire almeno uno tra codice fiscale e numero di telefono per il familiare da inserire", HttpStatus.BAD_REQUEST);
		}
		
		/* controllo e ricerca del tipo documennto */
		if(StringUtils.isBlank(familiare.getDocumento()))
		{
			logger.info("Documento del familiare non valido");
			throw new SiapAfisMWException("Documento del familiare non valido", HttpStatus.BAD_REQUEST);
		}
		
		Integer idDocumento = null;
		try
		{
			idDocumento = Integer.valueOf(familiare.getDocumento());
		}
		
		catch(Throwable ex)
		{
			logger.info("Impossibile riconoscere il tipo di documento {} ",familiare.getDocumento(), ex);
			throw new SiapAfisMWException("Impossibile riconoscere il tipo di documento " + 
		                                       familiare.getDocumento(), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Ricerca tipo documento...");
		Optional<TipoDocumentoTMiddle> optTipoDocumento = this.tipoDocumentoTMiddleRepository
				                       .findByIdTipoDocumento(idDocumento);
		
		if(optTipoDocumento.isEmpty())
		{
			logger.info("Impossibile riconoscere il tipo di documento {}",familiare.getDocumento());
			throw new SiapAfisMWException("Impossibile riconoscere il tipo di documento " + 
		                                       familiare.getDocumento(), HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isBlank(familiare.getNumeroDocumento()))
		{
			logger.info("Numero documento del familiare non valido");
			throw new SiapAfisMWException("Numero documento del familiare non valido", HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isBlank(familiare.getTelefono()) /*|| !familiare.getTelefono().matches("^[0-9]+$" ) */)
		{
			logger.info("Telefono del familiare non valido");
			throw new SiapAfisMWException("Telefono del familiare non valido", HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isBlank(familiare.getDataDocumento()))
		{
			logger.info("Data documento non valida");
			throw new SiapAfisMWException("Data documento non valida", HttpStatus.BAD_REQUEST);
		}
		
		/* controllo e ricerca del grado familiare */
		if(StringUtils.isBlank(familiare.getGradoParentela()))
		{
			logger.info("Grado parentela non presente");
			throw new SiapAfisMWException("Grado parentela non presente", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Ricerca del grado parentela...");
		Optional<RelazioneParentelaTMiddle> optRelazione = this.parentelaRepository.findByIdParentelaIgnoreCase(familiare.getGradoParentela().toUpperCase());
		if(optRelazione.isEmpty())
		{
			logger.info("Impossibile trovare il grado di parentela {}. Si prova ad cercare ua relazione di default...", familiare.getGradoParentela());
			optRelazione = this.parentelaRepository.findByDescrizioneParentela(SiapAfisMWConstants.RELAZIONE_PARENTELA_NON_RILEVATA);
			if(optRelazione.isEmpty())
			{
				logger.info("Impossibile definire il grado di parentela per il familiare specificato");
				throw new SiapAfisMWException("Impossibile definire il grado di parentela per il familiare specificato", HttpStatus.BAD_REQUEST);
			}
		}
		
		logger.info("Trovata relazione di parentela {}", optRelazione.get().getDescrizioneParentela());
		
		/* controllo di conformita' e conversione della data */
		String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-(19|20)\\d\\d$";
		logger.info("Controllo data con regex {}", dateRegex);
		if(!familiare.getDataDocumento().matches(dateRegex))
		{
			logger.info("La data del documento non e' nel formato dd-MM-yyyy e/o contiene caratteri non validi");
			throw new SiapAfisMWException("La data del documento non e' nel formato dd-MM-yyyy", HttpStatus.BAD_REQUEST);
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dataDocumento = LocalDate.parse(familiare.getDataDocumento(), formatter);
		
		/* controllo detenuto */
		if(StringUtils.isBlank(matricola))
		{
			logger.info("Matricola del detenuto non presente");
			throw new SiapAfisMWException("Matricola del detenuto non presente", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Ricerca ID soggetto...");
		Optional<MatricolaTMiddle> idSoggetto = this.matricolaRepository.findById(matricola);
		if(idSoggetto.isEmpty())
		{
			logger.info("Nessun detenuto presente con la matricola specificata");
			throw new SiapAfisMWException("Nessun detenuto presente con la matricola specificata", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Controllo presenza associazione detenuto-familiare...");
		Integer idAssociazione = this.familiareTMiddleRepository.
				                               getProgressivoFamiliareAssociato(idSoggetto.get().getIdSoggetto(), 
				                               familiare.getTelefono(), familiare.getCodiceFiscale());
		if(idAssociazione == null)
		{
			/* ricerca massimo progressivo familiare per l'id soggetto trovato */
			logger.info("Ricerca massimo progressivo familiare...");
			Integer maxProgressivoFamiliare = this.familiareTMiddleRepository.getMaxPrgFamiliare(idSoggetto.get().getIdSoggetto());
			logger.info("Prossimo progressivo familiare che verra' utilizzato: {}", maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);
			
			logger.info("Ricerca del prossimo progressivo per l'inserimento del documeto...");
			Integer progressivoDocumento = this.documentoTMiddleRepository.getMaxProgressivoDocumento(
					                       idSoggetto.get().getIdSoggetto(), 
					                       maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);	
			
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
			familiareTM.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
			familiareTM.setDataInserimento(LocalDateTime.now());
			
			this.familiareTMiddleRepository.save(familiareTM);
			
			logger.info("Effettuata operazione SAVE del familiare");
			
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
			
			idAssociazione = maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1;
			
			logger.info("Effettuata operazione SAVE del documento");
		}
		
		else
			logger.info("Associazione detenuto-familiare gia' presente, "
					+ "si procede semplicemente all'inserimento di una nuova autorizzazione...");
		
		
		
		logger.info("Inizio inserimento autorizzazione...");
		logger.info("Ricerca progressivo per tabella MDD304_AUTORIZZ...");
		Integer nextProgressivoAutorizzazione = this.autorizzazioneTMiddleRepository.findMaxPrgByMatricola(matricola);
		if(nextProgressivoAutorizzazione == null)
			nextProgressivoAutorizzazione = 1;
		
		else
			nextProgressivoAutorizzazione++;
		
		logger.info("Prossimo progressivo: {}. Preparazione oggetto per salvataggio sul DB... ", nextProgressivoAutorizzazione);
		AutorizzazioneTMiddleId autorizzazioneId = new AutorizzazioneTMiddleId(matricola, nextProgressivoAutorizzazione);
		AutorizzazioneTMiddle autorizzazione = new AutorizzazioneTMiddle();
		autorizzazione.setAutorizzazioneTMiddleId(autorizzazioneId);
		autorizzazione.setFunzione(SiapAfisMWConstants.FUNZIONE_COLLOQUIO);
		autorizzazione.setTipo(SiapAfisMWConstants.TIPO_AUTORIZZAZIONE_PERMANENTE);
		autorizzazione.setData(LocalDate.now());
		autorizzazione.setDirAutor(SiapAfisMWConstants.DIR_AUTOR_DIRETTORE);
		autorizzazione.setIdSoggetto(idSoggetto.get().getIdSoggetto());
		autorizzazione.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
		autorizzazione.setDataInserimento(LocalDateTime.now());
		autorizzazione.setStato(SiapAfisMWConstants.STATO_AUTORIZZAZIONE_VALIDA);
		
		this.autorizzazioneTMiddleRepository.save(autorizzazione);
		
		logger.info("Preparazione oggetto per salvataggio autorizzazione familiare...");
		AutorizzazioneFamiliareTMiddleId familiareId = new AutorizzazioneFamiliareTMiddleId(matricola, 
				                                       nextProgressivoAutorizzazione, 
//				                                       maxProgressivoFamiliare != null ? maxProgressivoFamiliare + 1 : 1);
				                                       idAssociazione);
		
		AutorizzazioneFamiliareTMiddle autorizazioneFamiliare = new AutorizzazioneFamiliareTMiddle();
		autorizazioneFamiliare.setAutorizzazioneFamiliareTMiddleId(familiareId);
		autorizazioneFamiliare.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
		autorizazioneFamiliare.setDataInserimento(LocalDateTime.now());
		
		this.autorizzazioneFamiliareTMiddleRepository.save(autorizazioneFamiliare);
		
		logger.info("Inserimento dati di tracking...");
		this.trackingDAOImpl.storeTracking(TrackingOperation.SALVA_FAMILIARE, familiare, EsitoTracking.OK);
		
		logger.info("Fine metodo DAO per inserimento familiare");

	}

	@Override
	public Familiare getFamiliareByNumeroTelefono(String numeroTelefono) throws SiapAfisMWException 
	{
		/* controllo numero telefono */
		if(StringUtils.isBlank(numeroTelefono))
			throw new SiapAfisMWException("Telefono del familiare non valido", HttpStatus.BAD_REQUEST);
		
		/* ricerca familiare */
		Optional<Familiare> optFamiliare = this.familiareRepository.findById(numeroTelefono);
		if(optFamiliare.isEmpty())
			throw new SiapAfisMWException("Nessun familiare con il telefono fornito", HttpStatus.NOT_FOUND);
		
		return optFamiliare.get();
	}

	@Override
	public Familiare getFamiliareByCodiceFiscale(String codiceFiscale) throws SiapAfisMWException 
	{
		/* controllo numero telefono */
		if(StringUtils.isBlank(codiceFiscale))
			throw new SiapAfisMWException("Codice fiscale del familiare non valido", HttpStatus.BAD_REQUEST);
		
		/* ricerca familiare */
		Optional<Familiare> optFamiliare = this.familiareRepository.findByCodiceFiscale(codiceFiscale);
		if(optFamiliare.isEmpty())
			throw new SiapAfisMWException("Nessun familiare con il codice fiscale fornito", HttpStatus.BAD_REQUEST);
		
		return optFamiliare.get();
		
	}

	@Override
	public Familiare getFamiliareByNumeroTelefonoCodiceFiscale(SimpleRicercaDTO ricerca) throws SiapAfisMWException 
	{
		logger.info("Accesso alla funzione DAO per la ricerca del familiare sulla base del codice fiscale o del numero di telefono");
		
		if(StringUtils.isAllBlank(ricerca.getCodiceFiscale(), ricerca.getNumeroTelefono()))
		{
			logger.info("Fornire almeno uno tra codice fiscale e numero di telefono del familiare");
			throw new SiapAfisMWException("Fornire almeno uno tra codice fiscale e numero di telefono del familiare", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Preparazione query nativa...");
		String query = "SELECT mf.M301_NOME , mf.M301_COGNOME ,mf.M301_UTENZA , mf.M301_COD_FISCALE , mt.M_IDTIPODOC  , md.M302_NUM_DOC , mr.IDRELPARENTELA , MD.M302_DT_DOC "
				+ "FROM GATEWAY.MDD301_FAMILIARE mf  "
				+ "JOIN GATEWAY.MDC_RELPARENTELA mr ON mr.IDRELPARENTELA  = mf.M301_REL_PAR   "
				+ "JOIN GATEWAY.MDD302_DOCUMENTO md ON mf.M301_PRG_FAM = md.M302_PRG_FAM AND MF.M301_ID_SOGG = md.M302_ID_SOGG  "
				+ "JOIN GATEWAY.CSSC_TPDOC mt ON md.M302_TIPO_DOC = MT.M_IDTIPODOC  " 
				+ "WHERE (mf.M301_DT_CANC IS NULL  AND md.M302_DT_CANC IS NULL) ";
		
		List<Object[]> listaRisultati = null;
		
		if(StringUtils.isNotBlank(ricerca.getCodiceFiscale()))
		{
			logger.info("Rilevato codice fiscale, si usera' questa informazione per la ricerca del familiare");
			query += " AND mf.M301_COD_FISCALE = :codiceFiscale LIMIT 1";
			
			listaRisultati = entityManager.createNativeQuery(query).setParameter("codiceFiscale", 
					         ricerca.getCodiceFiscale()).getResultList();
		}
		
		else
		{
			logger.info("Rilevato numero di telefono, si usera' questa informazione per la ricerca del familiare");
			query += " AND MF.M301_UTENZA = :utenza LIMIT 1";
			
			listaRisultati = entityManager.createNativeQuery(query).setParameter("utenza", 
			         ricerca.getNumeroTelefono()).getResultList();
		}
		
		if(CollectionUtils.isEmpty(listaRisultati))
		{
			logger.info("Nessun risultato trovato con le informazioni fornite");
			//throw new SiapAfisMWException("Nessun risultato trovato con le informazioni fornite", HttpStatus.NOT_FOUND);
			return null;
		} 
		
		logger.info("Preparazione oggetto con informazioni familiare...");
		Familiare familiare = new Familiare();

		String pattern = "YYYY-MM-dd";
		String dataDoc = null;
		DateFormat simplDateFormat = new SimpleDateFormat(pattern);


		if(listaRisultati.get(0)[7] != null)
        {
			Date x = (Date)(listaRisultati.get(0)[7]);
	        dataDoc = simplDateFormat.format(x);
        }

		familiare.setNome(listaRisultati.get(0)[0] != null ? (String)listaRisultati.get(0)[0] : null);
		familiare.setCognome(listaRisultati.get(0)[1] != null ? (String)listaRisultati.get(0)[1] : null);
		familiare.setTelefono(listaRisultati.get(0)[2] != null ? (String)listaRisultati.get(0)[2] : null);
		familiare.setCodiceFiscale(listaRisultati.get(0)[3] != null ? (String)listaRisultati.get(0)[3] : null);
		
		familiare.setDocumento(listaRisultati.get(0)[4] != null ? String.valueOf((BigDecimal)listaRisultati.get(0)[4]) : null);
		familiare.setNumeroDocumento(listaRisultati.get(0)[5] != null ? (String)listaRisultati.get(0)[5] : null);
				
		familiare.setGradoParentela(listaRisultati.get(0)[6] != null ? String.valueOf((Character)listaRisultati.get(0)[6]) : null);
		
		familiare.setDataDocumento(dataDoc);
		
		return familiare;
	}

}
