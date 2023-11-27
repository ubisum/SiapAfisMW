package it.mgg.siapafismw.dao;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import it.mgg.siapafismw.dto.InsertUpdateDTO;
import it.mgg.siapafismw.dto.RicercaDetenutoDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.SalvaFamiliareTracking;
import it.mgg.siapafismw.model.tracking.GetFamiliareTracking;
import it.mgg.siapafismw.model.tracking.GetInfoDetenutoTracking;
import it.mgg.siapafismw.model.tracking.GetListaDetenutiTracking;
import it.mgg.siapafismw.model.tracking.InsertOrUpdateColloquioTracking;
import it.mgg.siapafismw.repositories.GetFamiliareTrackingRepository;
import it.mgg.siapafismw.repositories.GetInfoDetenutoRepository;
import it.mgg.siapafismw.repositories.GetListaDetenutiTrackingRepository;
import it.mgg.siapafismw.repositories.InsertOrUpdateColloquioTrackingRepository;
import it.mgg.siapafismw.repositories.SalvaFamiliareTrackingRepository;

@Component
public class TrackingDAOImpl implements TrackingDAO 
{
	@Autowired
	private GetFamiliareTrackingRepository getFamiliareRepository;
	
	@Autowired
	private GetInfoDetenutoRepository getInfoDetenutoRepository;
	
	@Autowired
	private GetListaDetenutiTrackingRepository getListaDetenutiRepository;
	
	@Autowired
	private InsertOrUpdateColloquioTrackingRepository insertOrUpdateRepository;
	
	@Autowired
	private SalvaFamiliareTrackingRepository salvaFamiliareRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TrackingDAOImpl.class);
	
	@Override
	public void storeTracking(TrackingOperation operation, Object data, EsitoTracking esito) throws SiapAfisMWException 
	{
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
				tracking.setGetFamiliareTrackingId(2);
				
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
				listaDetenutiTracking.setCodiceFiscale(ricercaListaDetenuti.getCodiceFiscale());
				listaDetenutiTracking.setDataInserimento(LocalDateTime.now());
				listaDetenutiTracking.setEsito(esito.getEsitoTracking());
				listaDetenutiTracking.setNumTelefono(ricercaListaDetenuti.getNumeroTelefono());
				
				this.getListaDetenutiRepository.save(listaDetenutiTracking);
				
				break;
				
			case INSERT_UPDATE_COLLOQUIO:
				if(!(data instanceof InsertUpdateDTO))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				InsertUpdateDTO insert = (InsertUpdateDTO)data;
				if(StringUtils.isBlank(insert.getId()))
				{
					logger.info("Identificativo del colloquio non presente");
					throw new SiapAfisMWException("Identificativo del colloquio non presente", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				logger.info("Inserimento record di tracking...");
				
				InsertOrUpdateColloquioTracking colloquio = new InsertOrUpdateColloquioTracking();
				colloquio.setColloquioId(insert.getId());
				colloquio.setDataInserimento(LocalDateTime.now());
				colloquio.setEsito(esito.getEsitoTracking());
				
				this.insertOrUpdateRepository.save(colloquio);
				
				break;
				
			case SALVA_FAMILIARE:
				if(!(data instanceof SalvaFamiliareTracking))
				{
					logger.info("L'oggetto fornito non e' adatto all'operazione richiesta");
					throw new SiapAfisMWException("L'oggetto fornito non e' adatto all'operazione richiesta", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				SalvaFamiliareTracking salvaFamiliare = (SalvaFamiliareTracking)data;
				
				this.salvaFamiliareRepository.save(salvaFamiliare);
				
				break;
				
			default:
				break;
		
		}

	}

}
