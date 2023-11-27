package it.mgg.siapafismw.utils;

import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SimpleRicercaDTO;
import org.apache.commons.collections4.CollectionUtils;

import it.mgg.siapafismw.dto.AllegatoDTO;
import it.mgg.siapafismw.dto.FamiliareDTO;
import it.mgg.siapafismw.model.Allegato;
import it.mgg.siapafismw.model.Familiare;

import java.util.ArrayList;
import java.util.List;

public class ConvertionUtils
{
	public static FamiliareDTO convertFamiliare2DTO(Familiare familiare)
	{
		FamiliareDTO familiareDTO = new FamiliareDTO();

		familiareDTO.setCodiceFiscale(familiare.getCodiceFiscale());
		familiareDTO.setCognome(familiare.getCognome());
		familiareDTO.setDocumento(familiare.getDocumento());
		familiareDTO.setNome(familiare.getNome());
		familiareDTO.setNumeroDocumento(familiare.getNumeroDocumento());
		familiareDTO.setDataDocumento(familiare.getDataDocumento());
		familiareDTO.setTelefono(familiare.getTelefono());
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
}
