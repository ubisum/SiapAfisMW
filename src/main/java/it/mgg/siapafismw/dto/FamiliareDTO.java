package it.mgg.siapafismw.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliareDTO 
{
	private String nome;
	private String cognome;
	private String telefono;
	private String codiceFiscale;
	private String documento;
	private String numeroDocumento;
	private List<AllegatoDTO> allegati;
}
