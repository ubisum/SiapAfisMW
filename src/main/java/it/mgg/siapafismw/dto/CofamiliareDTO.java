package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CofamiliareDTO 
{
	private String codiceFiscale;
	private String nome;
	private String cognome;
	private String gradoParentela;
}
