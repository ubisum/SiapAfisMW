package it.mgg.siapafismw.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.ColloquioDTO;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.ColloquioFamiliareTMiddle;
import it.mgg.siapafismw.model.ColloquioFamiliareTMiddleId;
import it.mgg.siapafismw.model.ColloquioTMiddle;
import it.mgg.siapafismw.model.ColloquioTMiddleId;
import it.mgg.siapafismw.model.IDDataVerseMapping;
import it.mgg.siapafismw.model.MatricolaTMiddle;
import it.mgg.siapafismw.repositories.ColloquioFamiliareTMIddleRepository;
import it.mgg.siapafismw.repositories.ColloquioTMiddleRepository;
import it.mgg.siapafismw.repositories.FamiliareTMiddleRepository;
import it.mgg.siapafismw.repositories.IDDataVerseMappingRepository;
import it.mgg.siapafismw.repositories.MatricolaTMiddleRepository;
import it.mgg.siapafismw.utils.SiapAfisMWConstants;
import jakarta.transaction.Transactional;

@Service("colloquioServiceImpl")
public class ColloquioServiceImpl implements ColloquioService 
{
	@Autowired
	private IDDataVerseMappingRepository idDataVerseMappingRepository;
	
	@Autowired
	private ColloquioFamiliareTMIddleRepository colloquioFamiliareTMIddleRepository;
	
	@Autowired
	private ColloquioTMiddleRepository colloquioTMiddleRepository;
	
	@Autowired
	private MatricolaTMiddleRepository matricolaTMiddleRepository;
	
	@Autowired
	private FamiliareTMiddleRepository familiareTMiddleRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(ColloquioServiceImpl.class);
	
	@Override
	@Transactional
	public void insertUpdateColloquio(ColloquioDTO insertUpdate) throws SiapAfisMWException 
	{
		logger.info("Inizio procedura di inserimento/aggiornamento del colloquio...");
		
		if(StringUtils.isBlank(insertUpdate.getIdColloquioDataVerse()))
		{
			logger.info("Identificativo Dataverse non presente");
			throw new SiapAfisMWException("Identificativo Dataverse non presente", HttpStatus.BAD_REQUEST);
		}
		
		if(StringUtils.isBlank(insertUpdate.getMatricola()) || insertUpdate.getMatricola().length() != 11)
		{
			logger.info("Matricola fornita non valida");
			throw new SiapAfisMWException("Matricola fornita non valida", HttpStatus.BAD_REQUEST);
		}
		
		Optional<MatricolaTMiddle> optMatricola = this.matricolaTMiddleRepository
                .findById(insertUpdate.getMatricola());

		if(optMatricola.isEmpty())
		{
			logger.info("Impossibile trovare dati per la matricola fornita");
			throw new SiapAfisMWException("Impossibile trovare dati per la matricola fornita", HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Ricerca dati locali sulla base dell'ID colloquio Dataverse...");
		Optional<IDDataVerseMapping> optIdColloquio = this.idDataVerseMappingRepository
				                     .findById(insertUpdate.getIdColloquioDataVerse());
		
		logger.info("Controllo del familiare all'indice 1...");
		if(StringUtils.isBlank(insertUpdate.getCodiceFiscaleFamiliare1()) && StringUtils.isBlank(insertUpdate.getNumeroTelefonoFamiliare1()))
		{
			logger.info("E' necessario inserire il codice fiscale o il nunmero di telefono almeno"
					+ " per il familiare all'indice 1.");
			
			throw new SiapAfisMWException("E' necessario inserire il codice fiscale o il nunmero di telefono almeno"
					+ " per il familiare all'indice 1.", HttpStatus.BAD_REQUEST);
		}
		
		/* variabili di lavoro */
		ColloquioFamiliareTMiddle colloquioFamiliare = null;
		ColloquioTMiddle colloquio = null;
		Integer nextProgressivoColloquio = null;
		Integer nextProgressivoColloquioFamiliare = null;
		
		if(optIdColloquio.isPresent())
		{
			logger.info("Trovati dati locali relativamente all'ID {}", insertUpdate.getIdColloquioDataVerse());
			logger.info("Controllo dati associati...");
			
			if(StringUtils.isBlank(optIdColloquio.get().getMatricola()) ||
			  optIdColloquio.get().getPrgColloquio() == null ||
			  optIdColloquio.get().getPrgColloquioFam() == null)
			{
				logger.info("Dati associati all'ID non validi");
				throw new SiapAfisMWException("Dati associati all'ID non validi", 
						                      HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(!optIdColloquio.get().getMatricola().equals(insertUpdate.getMatricola()))
			{
				logger.info("La matricola {} fornita non corrisponde a quella associata, "
						+ "sui dati locali, all'ID {}",
						insertUpdate.getMatricola(),
						insertUpdate.getIdColloquioDataVerse());
				
				throw new SiapAfisMWException(String.format("La matricola %s fornita non corrisponde a quella associata, "
						+ "sui dati locali, all'ID %s", 
						insertUpdate.getMatricola(),
						insertUpdate.getIdColloquioDataVerse()), HttpStatus.BAD_REQUEST);
			}
			
			logger.info("Ricerca colloquio...");
			colloquio = this.colloquioTMiddleRepository.findByMatricolaProgressivo(
					               optIdColloquio.get().getMatricola(), 
					               optIdColloquio.get().getPrgColloquio());
			
			if(colloquio == null)
			{
				logger.info("Impossibile trovare il colloquio");
				throw new SiapAfisMWException("Impossibile trovare il colloquio", 
						                      HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			logger.info("Ricerca colloquio familiare...");
			colloquioFamiliare = this.colloquioFamiliareTMIddleRepository.fidByMatricolaProgressivo(
					             optIdColloquio.get().getMatricola(), 
					             optIdColloquio.get().getPrgColloquioFam());
			
			if(colloquioFamiliare == null)
			{
				logger.info("Impossibile trovare il colloquio familiare");
				throw new SiapAfisMWException("Impossibile trovare il colloquio familiare", 
						                      HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		else
		{
			logger.info("Nessun dato trovato relativamente all'ID {}", 
					    insertUpdate.getIdColloquioDataVerse());
			
			logger.info("Si procede all'inserimento di un nuovo colloquio...");
			
			logger.info("Creazione oggetti per la persistenza...");
			colloquio = new ColloquioTMiddle();
			colloquioFamiliare = new ColloquioFamiliareTMiddle();
			
			logger.info("Ricerca dei dati associati alla matricola {}...",
					    insertUpdate.getMatricola());
			
			logger.info("Ricerca dei progressivi per gli oggetti di persistenza...");
			nextProgressivoColloquio = this.colloquioTMiddleRepository
					                            .getMaxProgressivoFromMatricola(insertUpdate.getMatricola());
			
			if(nextProgressivoColloquio == null)
				nextProgressivoColloquio = 1;
			
			else
				nextProgressivoColloquio++;
			
			nextProgressivoColloquioFamiliare = this.colloquioFamiliareTMIddleRepository
					                                    .getMaxProgressivoByMatricola(insertUpdate.getMatricola());
			
			if(nextProgressivoColloquioFamiliare == null)
				nextProgressivoColloquioFamiliare = 1;
			
			else
				nextProgressivoColloquioFamiliare++;
			
			logger.info("Creazione delle chiavi primarie degli oggetti di persistenza...");
			colloquioFamiliare.setColloquioId(new ColloquioFamiliareTMiddleId(insertUpdate.getMatricola(), 
					                                                          nextProgressivoColloquioFamiliare));
			
			logger.info("Inserimento, negli oggetti di persistenza, delle informazioni obbligatorie "
					  + "non provenienti dai dati di input...");
			
			colloquioFamiliare.setIdSoggetto(optMatricola.get().getIdSoggetto());
			colloquioFamiliare.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
			colloquioFamiliare.setDataInserimento(LocalDateTime.now());
			
			colloquio.setLoginIns(SiapAfisMWConstants.DEFAULT_OPERATOR);
			colloquio.setDataInserimento(LocalDateTime.now());
			
			
		}
		
		logger.info("Inserimento/aggiornamento dei dati in ingresso...");
		
		logger.info("Controllo data...");
		LocalDateTime dataColloquio = null;
		if(StringUtils.isBlank(insertUpdate.getData()))
		{
			logger.info("Data colloquio non presente");
			throw new SiapAfisMWException("Data colloquio non presente", HttpStatus.BAD_REQUEST);
		}
		
		try
		{	
			ZonedDateTime zdt = ZonedDateTime.parse(insertUpdate.getData());
			dataColloquio = zdt.toLocalDateTime();
			
			if(optIdColloquio.isEmpty())
				colloquio.setColloquioId(new ColloquioTMiddleId(insertUpdate.getMatricola(), nextProgressivoColloquio, 0, dataColloquio));
		}
		
		catch(Throwable ex)
		{
			logger.info("Impossibile acquisire la data {}", insertUpdate.getData(), ex);
			throw new SiapAfisMWException("Impossibile acquisire la data " + insertUpdate.getData(), HttpStatus.BAD_REQUEST);
		}
		
		colloquioFamiliare.setData(dataColloquio.toLocalDate());
		
		logger.info("Controllo ed inserimento dello stato...");
		if(StringUtils.isBlank(insertUpdate.getStato()) || !Arrays.asList("A", "R", "E").contains(insertUpdate.getStato()))
		{
			logger.info("Stato fornito non valido");
			throw new SiapAfisMWException("Stato fornito non valido", HttpStatus.BAD_REQUEST);
		}
		
		colloquio.setStato(insertUpdate.getStato());
		colloquioFamiliare.setStato(insertUpdate.getStato());
		
		logger.info("Controllo ed inserimento del tipo...");
		if(StringUtils.isBlank(insertUpdate.getTipo()) || !Arrays.asList("O", "S").contains(insertUpdate.getTipo()))
		{
			logger.info("Tipo fornito non valido");
			throw new SiapAfisMWException("Tipo fornito non valido", HttpStatus.BAD_REQUEST);
		}
		
		colloquioFamiliare.setTipo(insertUpdate.getTipo());
		
		logger.info("Controllo modalita'...");
		if(StringUtils.isBlank(insertUpdate.getModalita()) || !Arrays.asList("P", "V").contains(insertUpdate.getModalita()))
		{
			logger.info("La modalita' fornita non e' valida");
			throw new SiapAfisMWException("La modalita' fornita non e' valida", HttpStatus.BAD_REQUEST);
			
		}
		
		if(insertUpdate.getModalita().equals("V"))
		{
			logger.info("Modalita' videocolloquio rilevata, inserimento flag relativi...");
			colloquioFamiliare.setVideoColloquio("S");
			colloquioFamiliare.setIdModalita(2);
		}
		
		logger.info("Controllo ore richieste...");
		if(insertUpdate.getOreRichieste() == null || insertUpdate.getOreRichieste() <= 0)
		{
			logger.info("Valore delle ore richieste non valido");
			throw new SiapAfisMWException("Valore delle ore richieste non valido", HttpStatus.BAD_REQUEST);
		}
		
		colloquioFamiliare.setOreColloquioRichieste(insertUpdate.getOreRichieste());
		
		if(insertUpdate.getOreEffettive() != null)
		{
			if(insertUpdate.getOreEffettive() < 0)
			{
				logger.info("Valore delle ore effettive non valido");
				throw new SiapAfisMWException("Valore delle ore effettive non valido", HttpStatus.BAD_REQUEST);
			}
			
			logger.info("Inserimento ore effettive...");
			colloquioFamiliare.setOreColloquioEffettive(insertUpdate.getOreEffettive());
		}
		
		
		logger.info("Inserimento ora inizio e fine colloquio...");
		try
		{
			DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
			
			if(StringUtils.isNotBlank(insertUpdate.getOraInizioColloquio()))
			{
				colloquio.setHhInizio(LocalTime.parse(insertUpdate.getOraInizioColloquio(), formatter));
				logger.info("Inserita ora inizio colloquio...");
			}
			
			if(StringUtils.isNotBlank(insertUpdate.getOraFineColloquio()))
			{
				colloquio.setHhFine(LocalTime.parse(insertUpdate.getOraFineColloquio(), formatter));
				logger.info("Inserita ora fine colloquio...");
			}
		}
		
		catch(Throwable ex)
		{
			logger.info("Impossibile effettuare il parsing dell'ora di inizio o di fine del colloquio. "
					  + "Controllare che siano nel formato HH:mm o HH:mm:ss.");
			
			throw new SiapAfisMWException("Impossibile effettuare il parsing dell'ora di inizio o di fine del colloquio. "
					                    + "Controllare che siano nel formato HH:mm o HH:mm:ss.", 
					                      HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Analisi dei dati dei familiari...");
		try 
		{
			for(int index = 1; index <= 10; index++)
			{
				/* creazione dei descrittori associati ai campi di interesse */
				PropertyDescriptor propertyDescriptorCF = new PropertyDescriptor("codiceFiscaleFamiliare" + index, 
						                                                         ColloquioDTO.class);
				
				PropertyDescriptor propertyDescriptorTel = new PropertyDescriptor("numeroTelefonoFamiliare" + index, 
                                                                                  ColloquioDTO.class);
				
				/* accesso ai metodi relativi ai campi di interesse */
				Method getterCF = propertyDescriptorCF.getReadMethod();
				Method getterTel = propertyDescriptorTel.getReadMethod();
				
				/* accesso ai valori dei campi di interesse */
				Object codiceFiscale = getterCF.invoke(insertUpdate);
				Object numeroTelefono = getterTel.invoke(insertUpdate);
				
				if((codiceFiscale != null && StringUtils.isNotBlank((String)codiceFiscale)) || 
					(numeroTelefono != null && StringUtils.isNotBlank((String)numeroTelefono)))
				{
					logger.info("Analisi del familiare con dati di ricerca (TELEFONO, CODICE FISCALE) = ({}, {})", 
							numeroTelefono != null && StringUtils.isNotBlank((String)numeroTelefono)? (String)numeroTelefono : "NULL",
							codiceFiscale != null && StringUtils.isNotBlank((String)codiceFiscale) ? (String)codiceFiscale : "NULL");
					
					Integer progressivo = this.familiareTMiddleRepository.getProgressivoFamiliareAssociato(
							                        optMatricola.get().getIdSoggetto(), 
							                        numeroTelefono != null ? (String)numeroTelefono : null, 
							                        codiceFiscale != null ? (String)codiceFiscale : null);
					
					if(progressivo == null)
					{
						logger.info("Impossibile trovare il progressivo che mette in relazione il detenuto {} "
								  + "con il familiare i cui dati sono nella coppia di campi ({}, {})",
								  insertUpdate.getMatricola(),
								  "codiceFiscaleFamiliare" + index,
								  "numeroTelefonoFamiliare" + index);
						
						String messaggioEccezione = "Impossibile trovare il progressivo che mette in relazione il detenuto %s "
								  + "con il familiare i cui dati sono nella coppia di campi (%s, %s)";
						
						throw new SiapAfisMWException(String.format(
								                      messaggioEccezione, 
								                      insertUpdate.getMatricola(),
								                      "codiceFiscaleFamiliare" + index,
								                      "numeroTelefonoFamiliare" + index), HttpStatus.BAD_REQUEST);
					}
					
					logger.info("Trovato progressivo familiare {}", progressivo);
					
					logger.info("Inserimento del progressivo nel campo {} del colloquio familiare...", "prgFam" + index);
					
					/* descrittore della classe destinataria */
					PropertyDescriptor propertyDescriptorColl = new PropertyDescriptor("prgFam" + index, 
                                                                                       ColloquioFamiliareTMiddle.class);
					
					/* setter della classe destinataria */
					Method setter = propertyDescriptorColl.getWriteMethod();
					
					/* inserimento del valore nel rispettivo campo */
					setter.invoke(colloquioFamiliare, progressivo);
				}
				
				
			}
		} 
		
		catch (Throwable ex) 
		{
			logger.info("Si e' verificato un errore durante l'analisi dei dati dei familiari", ex);
			throw new SiapAfisMWException("Si e' verificato un errore durante l'analisi dei dati dei familiari", 
					                      HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		logger.info("Salvataggio dei dati del colloquio...");
		this.colloquioFamiliareTMIddleRepository.save(colloquioFamiliare);
		this.colloquioTMiddleRepository.save(colloquio);
		
		if(optIdColloquio.isEmpty())
		{
			logger.info("Creazione di un nuovo record associato all'ID DataVerse...");
			IDDataVerseMapping mapping = new IDDataVerseMapping();
			mapping.setIdColloquioDataVerse(insertUpdate.getIdColloquioDataVerse());
			mapping.setMatricola(insertUpdate.getMatricola());
			mapping.setPrgColloquio(nextProgressivoColloquio);
			mapping.setPrgColloquioFam(nextProgressivoColloquioFamiliare);
			
			this.idDataVerseMappingRepository.save(mapping);
		}

	}

}
