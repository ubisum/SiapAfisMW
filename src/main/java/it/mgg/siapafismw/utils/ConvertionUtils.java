package it.mgg.siapafismw.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import it.mgg.siapafismw.model.Allegato;
import it.mgg.siapafismw.model.Familiare;
import it.mgg.siapafismw.model.tracking.SalvaFamiliareTracking;

public class ConvertionUtils
{
	public static FamiliareDTO convertFamiliare2DTO(Familiare familiare)
	{
		FamiliareDTO familiareDTO = new FamiliareDTO();

		familiareDTO.setCodiceFiscale(familiare.getCodiceFiscale() != null ? familiare.getCodiceFiscale() : "");
		familiareDTO.setCognome(familiare.getCognome());
		familiareDTO.setDocumento(familiare.getDocumento());
		familiareDTO.setNome(familiare.getNome());
		familiareDTO.setNumeroDocumento(familiare.getNumeroDocumento());
		familiareDTO.setDataDocumento(familiare.getDataDocumento());
		familiareDTO.setTelefono(familiare.getTelefono() != null ? familiare.getTelefono() : "");
		
		familiareDTO.setGradoParentela(familiare.getGradoParentela());

		if(CollectionUtils.isNotEmpty(familiare.getListaAllegati()))
		{
			List<AllegatoDTO> listaDTO = new ArrayList<>();

			for(Allegato allegato : familiare.getListaAllegati())
			{
				AllegatoDTO dto = new AllegatoDTO();
				dto.setNomeFile(allegato.getNomeFile());
				dto.setTipo(allegato.getTipo());

				listaDTO.add(dto);
			}

			familiareDTO.setAllegati(listaDTO);
		}

		return familiareDTO;
	}

    public static RicercaDTO convertSimpleRicercaDTOtoRicercaDTO(SimpleRicercaDTO simpleRicerca) {
        RicercaDTO ricerca = new RicercaDTO();
        ricerca.setCodiceFiscaleFamiliare(simpleRicerca.getCodiceFiscale());
        ricerca.setNumeroTelefonoFamiliare(simpleRicerca.getNumeroTelefono());
        return ricerca;
    }
    
    public static SalvaFamiliareTracking convertFamiliareDTO2SalvaFamiliareTracking(FamiliareDTO familiare)
    {
    	/* definizione converter della data */
    	Converter<String, LocalDate> converter = new AbstractConverter<String, LocalDate>() 
    	{
    		public LocalDate convert(String source)
    		{
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    			return LocalDate.parse(source, formatter);
    			
    		}
		};
		
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(converter);
		
		return mapper.map(familiare, SalvaFamiliareTracking.class);
    }
}
