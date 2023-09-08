package it.mgg.siapafismw.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dao.DetenutoDAO;
import it.mgg.siapafismw.dto.DetenutoDTO;
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

}
