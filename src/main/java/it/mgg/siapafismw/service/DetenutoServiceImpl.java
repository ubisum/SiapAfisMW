package it.mgg.siapafismw.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import it.mgg.siapafismw.dao.DetenutoDAO;
import it.mgg.siapafismw.dao.TrackingDAO;
import it.mgg.siapafismw.dto.AvailabilityDTO;
import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;
import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;
import it.mgg.siapafismw.model.Detenuto;

@Service("detenutoServiceImpl")
public class DetenutoServiceImpl implements DetenutoService
{
	@Autowired
	private DetenutoDAO detenutoDAO;
	
	@Autowired
	private TrackingDAO trackingDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(DetenutoServiceImpl.class);

	@Override
	public DetenutoDTO findDetenutoByMatricola(String matricola) throws SiapAfisMWException 
	{
		logger.info("Accesso al servizio di ricerca del deteuto per matricola...");
		return detenutoDAO.findDetenutoByMatricola(matricola);
	}

	@Override
	public List<DetenutoDTO> getAllDetenuti() 
	{
		/* creazione lista di output */
		List<DetenutoDTO> listaOutput = new ArrayList<>();
		
		/* ricerca dei detenuti sul DB */
		List<Detenuto> listaDB = detenutoDAO.getAllDetenuti();
		
		if(CollectionUtils.isNotEmpty(listaDB))
		{
			ModelMapper mapper = new ModelMapper();
			for(Detenuto detenuto : listaDB)
				listaOutput.add(mapper.map(detenuto, DetenutoDTO.class));
		}
		
		return listaOutput;
	}

	@Override
	public List<DetenutoDTO> getDetenutiByNumeroTelefono(String numeroTelefono) throws SiapAfisMWException 
	{
		/* ricerca dei detenuti */
		List<Detenuto> listaDetenuti = this.detenutoDAO.getDetenutiByNumeroTelefono(numeroTelefono);
		
		/* mapping */
		List<DetenutoDTO> listaOutput = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(listaDetenuti))
		{
			ModelMapper mapper = new ModelMapper();
			for(Detenuto detenuto : listaDetenuti)
			{
				listaOutput.add(mapper.map(detenuto, DetenutoDTO.class));
			}
		}
		
		return listaOutput;
	}

	@Override
	public SlotDisponibileDTO getSlotDisponibili(String matricola) throws SiapAfisMWException 
	{
		/* ricerca detenuto */
		DetenutoDTO optDetenuto = detenutoDAO.findDetenutoByMatricola(matricola);
		if(optDetenuto == null)
			throw new IllegalArgumentException("Nessun detenuto trovato con la matricola fornita");
		
		SlotDisponibileDTO slot = new SlotDisponibileDTO();
		slot.setNome(optDetenuto.getNome());
		slot.setCognome(optDetenuto.getCognome());
		slot.setMatricola(optDetenuto.getMatricola());
		slot.setPenitenziario(optDetenuto.getPenitenziario());
		slot.setSezione(optDetenuto.getSezione());
		
		/* mock temporaneo */
		List<AvailabilityDTO> disponibilita = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		disponibilita.add(new AvailabilityDTO(formatter.format(new Date()), "9"));
		disponibilita.add(new AvailabilityDTO(formatter.format(new Date()), "12"));
		disponibilita.add(new AvailabilityDTO(formatter.format(new Date()), "20"));
		
		slot.setAvailability(disponibilita);
		
		return slot;
			
	}

	@Override
	public List<DetenutoDTO> findDetenutiByCFNumeroTelefono(@RequestBody RicercaDTO ricerca) throws SiapAfisMWException 
	{
		/* ricerca detenuti nel DAO */
		logger.info("Accesso al servizio per la ricerca del detenunto sulla base del codice fiscale o del numero di telefono");
		List<Detenuto> listaDetenuti = detenutoDAO.findDetenutiByCFNumeroTelefono(ricerca);
		
		/* lista output */
		List<DetenutoDTO> listaDTO = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(listaDetenuti))
		{
			logger.info("Trovati {} detenuti. Inizio conversione dati in DTO...", listaDetenuti.size());
			ModelMapper mapper = new ModelMapper();
			
			for(Detenuto detenuto : listaDetenuti)
				listaDTO.add(mapper.map(detenuto, DetenutoDTO.class));
			
		}
		
		else
			logger.info("Nessun detenuto trovato");
		
		/* tracking */
//		this.trackingDAO.storeTracking(TrackingOperation.GET_LISTA_DETENUTI, 
//				                       new SimpleRicercaDTO(ricerca.getNumeroTelefonoFamiliare(), ricerca.getCodiceFiscaleFamiliare()), EsitoTracking.OK);
//		
		
		return listaDTO;
	}

}
