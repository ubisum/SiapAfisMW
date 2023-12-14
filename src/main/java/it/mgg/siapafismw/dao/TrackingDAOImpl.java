package it.mgg.siapafismw.dao;

import java.time.LocalDateTime;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDetenutoDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.tracking.GetFamiliareTracking;
import it.mgg.siapafismw.model.tracking.GetInfoDetenutoTracking;
import it.mgg.siapafismw.model.tracking.GetListaDetenutiTracking;
import it.mgg.siapafismw.model.tracking.InsertOrUpdateColloquioTracking;
import it.mgg.siapafismw.model.tracking.SalvaFamiliareAllegatiTracking;
import it.mgg.siapafismw.model.tracking.SalvaFamiliareTracking;
import it.mgg.siapafismw.repositories.GetFamiliareTrackingRepository;
import it.mgg.siapafismw.repositories.GetInfoDetenutoTrackingRepository;
import it.mgg.siapafismw.repositories.GetListaDetenutiTrackingRepository;
import it.mgg.siapafismw.repositories.InsertOrUpdateColloquioTrackingRepository;
import it.mgg.siapafismw.repositories.SalvaFamiliareAllegatiTrackingRepository;
import it.mgg.siapafismw.repositories.SalvaFamiliareTrackingRepository;
import it.mgg.siapafismw.utils.ConvertionUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class TrackingDAOImpl implements TrackingDAO 
{
	@Autowired
	private GetFamiliareTrackingRepository getFamiliareRepository;
	
	@Autowired
	private GetInfoDetenutoTrackingRepository getInfoDetenutoRepository;
	
	@Autowired
	private GetListaDetenutiTrackingRepository getListaDetenutiRepository;
	
	@Autowired
	private InsertOrUpdateColloquioTrackingRepository insertOrUpdateRepository;
	
	@Autowired
	private SalvaFamiliareTrackingRepository salvaFamiliareRepository;
	
	@Autowired
	private SalvaFamiliareAllegatiTrackingRepository salvaFamiliareAllegatiTrackingRepository;
	
	@Value("${siapafismw.tracking.active}")
	private String trackingActive;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(TrackingDAOImpl.class);
	
	@Override
	public void storeTracking(TrackingOperation operation, Object data, EsitoTracking esito) throws SiapAfisMWException 
	{
		if(StringUtils.isBlank(trackingActive) || !trackingActive.equalsIgnoreCase("true"))
		{
			logger.warn("Tracking dei servizi non attivo");
			return;
		}
		
		/* individuazione dell'operazione richiesta */
		if(operation == null)
		{
			logger.info("Impossibile individuare l'operazione di tracking da eseguire");
			throw new SiapAfisMWException("Impossibile individuare l'operazione di tracking da eseguire", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String requestedOperation = operation.getOperation();
		
		/* controllo dati da archiviare */
		if(data == null)
		{
			logger.info("I dati da archiviare non sono validi");
			throw new SiapAfisMWException("I dati da archiviare non sono validi", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* controllo esito da inserire */
		if(esito == null)
		{
			logger.info("Esito dell'operazione, da inserire nel record di tracking, non valido");
			throw new SiapAfisMWException("Esito dell'operazione, da inserire nel record di tracking, non valido", 
					                      HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("Avvio dell'operazione di tracking {}...", requestedOperation);
		
		/* oggetti di lavoro */
		ModelMapper mapper = new ModelMapper();
		
		/* gestione delle casistiche di tracking */
		switch(operation)
		{
			case GET_FAMILIARE:
				if(!(data instanceof SimpleRicercaDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				SimpleRicercaDTO ricercaFamiliare = (SimpleRicercaDTO)data;
				if(StringUtils.isAllBlank(ricercaFamiliare.getCodiceFiscale(), ricercaFamiliare.getNumeroTelefono()))
				{
					logger.info("E' necessario fornire almeno un codice fiscale o un numero di telefono per l'operazione richiesta");
					throw new SiapAfisMWException("E' necessario fornire almeno un codice fiscale o un numero di telefono per l'operazione richiesta", 
							                      HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				logger.info("Inserimento record di tracking...");
				
				GetFamiliareTracking tracking = new GetFamiliareTracking();
				tracking.setCodiceFiscale(ricercaFamiliare.getCodiceFiscale());
				tracking.setDataInserimento(LocalDateTime.now());
				tracking.setEsito(esito.getEsitoTracking());
				tracking.setNumTelefono(ricercaFamiliare.getNumeroTelefono());
				tracking.setGetFamiliareTrackingId(getSequenceNextVal(operation));
				
				this.getFamiliareRepository.save(tracking);
				
				break;
				
			case GET_INFO_DETENUTO:
				if(!(data instanceof RicercaDetenutoDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				RicercaDetenutoDTO ricercaDetenuto = (RicercaDetenutoDTO)data;
				if(StringUtils.isBlank(ricercaDetenuto.getMatricola()))
				{
					logger.info("Matricola detenuto non valida");
					throw new SiapAfisMWException("Matricola detenuto non valida", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				logger.info("Inserimento record di tracking...");
				
				GetInfoDetenutoTracking detenutoTracking = new GetInfoDetenutoTracking();
				detenutoTracking.setDataInserimento(LocalDateTime.now());
				detenutoTracking.setEsito(esito.getEsitoTracking());
				detenutoTracking.setMatricola(ricercaDetenuto.getMatricola());
				detenutoTracking.setGetInfoDetenutoTrackingId(this.getSequenceNextVal(operation));
				
				this.getInfoDetenutoRepository.save(detenutoTracking);
				
				break;
				
			case GET_LISTA_DETENUTI:
				if(!(data instanceof SimpleRicercaDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				SimpleRicercaDTO ricercaListaDetenuti = (SimpleRicercaDTO)data;
				if(StringUtils.isAllBlank(ricercaListaDetenuti.getCodiceFiscale(), ricercaListaDetenuti.getNumeroTelefono()))
				{
					logger.info("E' necessario fornire almeno un codice fiscale o un numero di telefono per l'operazione richiesta");
					throw new SiapAfisMWException("E' necessario fornire almeno un codice fiscale o un numero di telefono per l'operazione richiesta", 
							                      HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				logger.info("Inserimento record di tracking...");
				
				GetListaDetenutiTracking listaDetenutiTracking = new GetListaDetenutiTracking();
				listaDetenutiTracking.setGetListaDetenutiTrackingId(this.getSequenceNextVal(operation));
				listaDetenutiTracking.setCodiceFiscale(ricercaListaDetenuti.getCodiceFiscale());
				listaDetenutiTracking.setDataInserimento(LocalDateTime.now());
				listaDetenutiTracking.setEsito(esito.getEsitoTracking());
				listaDetenutiTracking.setNumTelefono(ricercaListaDetenuti.getNumeroTelefono());
				
				this.getListaDetenutiRepository.save(listaDetenutiTracking);
				
				break;
				
			case INSERT_UPDATE_COLLOQUIO:
				if(!(data instanceof ColloquioDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				ColloquioDTO insert = (ColloquioDTO)data;
				
				logger.info("Inserimento record di tracking...");
				
				InsertOrUpdateColloquioTracking colloquio = mapper.map(insert, InsertOrUpdateColloquioTracking.class);
				colloquio.setInsertOrUpdateColloquioTrackingId(getSequenceNextVal(operation));
				colloquio.setMatricola(insert.getMatricola());
				colloquio.setIdColloquioDataVerse(insert.getIdColloquioDataVerse());
				colloquio.setData(insert.getData());
				colloquio.setStato(insert.getStato());
				colloquio.setTipo(insert.getTipo());
				colloquio.setModalita(insert.getModalita());
				colloquio.setOreRichieste(insert.getOreRichieste());
				colloquio.setOreEffettive(insert.getOreRichieste());
				colloquio.setOraInizioColloquio(insert.getOraInizioColloquio());
				colloquio.setOraFineColloquio(insert.getOraFineColloquio());
				colloquio.setNumeroTelefonoFamiliare1(insert.getNumeroTelefonoFamiliare1());
				colloquio.setCodiceFiscaleFamiliare1(insert.getCodiceFiscaleFamiliare1());
				colloquio.setNumeroTelefonoFamiliare2(insert.getNumeroTelefonoFamiliare2());
				colloquio.setCodiceFiscaleFamiliare2(insert.getCodiceFiscaleFamiliare2());
				colloquio.setNumeroTelefonoFamiliare3(insert.getNumeroTelefonoFamiliare3());
				colloquio.setCodiceFiscaleFamiliare3(insert.getCodiceFiscaleFamiliare3());
				colloquio.setDataInserimento(LocalDateTime.now());
				
				this.insertOrUpdateRepository.save(colloquio);
				
				break;
				
			case SALVA_FAMILIARE:
				if(!(data instanceof FamiliareDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				/* inserimento dei dati base del familiare */
				FamiliareDTO salvaFamiliare = (FamiliareDTO)data;
				SalvaFamiliareTracking sfTracking = ConvertionUtils.convertFamiliareDTO2SalvaFamiliareTracking(salvaFamiliare);
				
				sfTracking.setSalvaFamiliareTrackingId(getSequenceNextVal(operation));
				sfTracking.setDataInserimento(LocalDateTime.now());
				
				sfTracking = this.salvaFamiliareRepository.save(sfTracking);
				
				/* inserimento dei dati dei documenti */
				if(CollectionUtils.isNotEmpty(salvaFamiliare.getAllegati()))
				{
					for(AllegatoDTO allegato : salvaFamiliare.getAllegati())
					{
						SalvaFamiliareAllegatiTracking allegatoTracking = new SalvaFamiliareAllegatiTracking();
						allegatoTracking.setDataInserimento(LocalDateTime.now());
						allegatoTracking.setNomeFile(allegato.getNomeFile());
						allegatoTracking.setSalvaFamiliare(sfTracking);
						allegatoTracking.setSalvaFamiliareAllegatiId(getSequenceNextVal(TrackingOperation.SALVA_FAMILIARE_ALLEGATI));
						allegatoTracking.setTipoFile(allegato.getTipo());
						
						this.salvaFamiliareAllegatiTrackingRepository.save(allegatoTracking);
					}
				}
				
				break;
				
			default:
				break;
		
		}

	}

	@Override
	public Integer getSequenceNextVal(TrackingOperation operation) 
	{
		logger.info("Generazione prossimo valore della sequence per tracking {}...", operation.getOperation());
		
		/* definizione stringa parametrica */
		String query = "SELECT GATEWAY.%s.nextval FROM SYSIBM.sysdummy1";
		
		switch(operation)
		{
			case GET_FAMILIARE:
				query = String.format(query, "VDC_GET_FAMILIARE_TRACKING_SEQ");
				break;
			case GET_INFO_DETENUTO:
				query = String.format(query, "VDC_GET_INFO_DETENUTO_TRACKING_SEQ");
				break;
			case GET_LISTA_DETENUTI:
				query = String.format(query, "VDC_GET_LISTA_DETENUTI_TRACKING_SEQ");
				break;
			case INSERT_UPDATE_COLLOQUIO:
				query = String.format(query, "VDC_INSERT_OR_UPDATE_COLLOQUIO_TRACKING_SEQ");
				break;
			case SALVA_FAMILIARE:
				query = String.format(query, "VDC_SALVA_FAMILIARE_TRACKING_SEQ");
				break;
			case SALVA_FAMILIARE_ALLEGATI:
				query = String.format(query, "VDC_SALVA_FAMILIARE_ALLEGATI_TRACKING_SEQ");
				break;
			default:
				break;
		
		}
		
		logger.info("Esecuzione query {}...", query);
		
		Integer nextVal = (Integer)entityManager.createNativeQuery(query).getSingleResult();
		
		return nextVal;
	}

}
