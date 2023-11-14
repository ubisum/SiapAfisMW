package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetListaDetenutiDTO 
{
	private String nome;
	private String cognome;
	private String matricola;
	private String penitenziario;
	private String sezione;
	
	
	
}
