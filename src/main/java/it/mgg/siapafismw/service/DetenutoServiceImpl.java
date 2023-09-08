package it.mgg.siapafismw.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.DetenutoDAO;
import it.mgg.siapafismw.dto.AvailabilityDTO;
import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;
import it.mgg.siapafismw.model.Detenuto;

@Service
public class DetenutoServiceImpl implements DetenutoService
{
	@Autowired
	private DetenutoDAO detenutoDAO;

	@Override
	public DetenutoDTO findDetenutoByMatricola(String matricola) 
	{
		/* invocazione del DAO per la ricerca del detenuto */
		Detenuto detenuto = detenutoDAO.findDetenutoByMatricola(matricola);
		
		/* output */
		DetenutoDTO detenutoDTO = null;
		
		if(detenuto != null)
		{
			ModelMapper mapper = new ModelMapper();
			detenutoDTO = mapper.map(detenuto, DetenutoDTO.class);
		}
		
		return detenutoDTO;
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
	public List<DetenutoDTO> getDetenutiByNumeroTelefono(String numeroTelefono) 
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
	public SlotDisponibileDTO getSlotDisponibili(String matricola) 
	{
		/* ricerca detenuto */
		Detenuto optDetenuto = detenutoDAO.findDetenutoByMatricola(matricola);
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

}
