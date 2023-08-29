package it.mgg.siapafismw.dto;

import java.util.List;

public class ListaDetenutiDTO 
{
	private List<DetenutoDTO> listaDetenuti;

	public ListaDetenutiDTO(List<DetenutoDTO> listaDetenuti) {
		super();
		this.listaDetenuti = listaDetenuti;
	}

	public List<DetenutoDTO> getListaDetenuti() {
		return listaDetenuti;
	}

	public void setListaDetenuti(List<DetenutoDTO> listaDetenuti) {
		this.listaDetenuti = listaDetenuti;
	}
	
	
}
