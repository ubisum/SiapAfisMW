package it.mgg.siapafismw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.mgg.siapafismw.dto.DetenutoDTO;
import it.mgg.siapafismw.dto.RicercaDTO;
import it.mgg.siapafismw.dto.SlotDisponibileDTO;

@Service("detenutoServiceMockImpl")
public class DetenutoServiceMockImpl implements DetenutoService {

	@Override
	public DetenutoDTO findDetenutoByMatricola(String matricola) 
	{
		/* MOCK */
		return new DetenutoDTO("Carlo", "Magno", "HJRP0345LAX", "CR FOSSANO", "C7", true);
	}

	@Override
	public List<DetenutoDTO> getAllDetenuti() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetenutoDTO> getDetenutiByNumeroTelefono(String numeroTelefono) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SlotDisponibileDTO getSlotDisponibili(String matricola) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DetenutoDTO> findDetenutiByCFNumeroTelefono(RicercaDTO ricerca) 
	{
		/* MOCK */
		List<DetenutoDTO> listaDetenuti = new ArrayList<>();
		DetenutoDTO det1 = new DetenutoDTO("Antonio", "Rossi", "ETR6780PLJ1", "CC GENOVA MARASSI", "A2", false); 
		DetenutoDTO det2 = new DetenutoDTO("Giovanni", "Bianchi", "67PO02HGK1K", "CR SALUZZO RODOLFO MORANDI", "B3", true);
		
		listaDetenuti.add(det1);
		listaDetenuti.add(det2);
		
		return listaDetenuti;
	}

}
