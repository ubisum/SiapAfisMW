package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RicercaDTO 
{
	private String numeroTelefonoFamiliare;
	private String codiceFiscaleFamiliare;
	private String matricola;
}
