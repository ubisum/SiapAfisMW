package it.mgg.siapafismw.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private String dataDocumento;
	private String gradoParentela;
	private String matricolaDetenutoAssociato;
	private List<AllegatoDTO> allegati;
	
	@JsonIgnore
	private Integer id;
	 
}
