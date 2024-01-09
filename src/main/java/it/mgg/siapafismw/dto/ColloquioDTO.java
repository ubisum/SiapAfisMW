package it.mgg.siapafismw.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColloquioDTO 
{
	private String matricola;
	private String idColloquioDataVerse;
	private String data;
	private String stato;
	private String tipo;
	private String modalita;
	private Integer oreRichieste;
	private Integer oreEffettive;
	private String oraInizioColloquio;
	private String oraFineColloquio;
	private String codiceFiscaleFamiliare1;
	private String numeroTelefonoFamiliare1;
	private String codiceFiscaleFamiliare2;
	private String numeroTelefonoFamiliare2;
	private String codiceFiscaleFamiliare3;
	private String numeroTelefonoFamiliare3;
	private String codiceFiscaleFamiliare4;
	private String numeroTelefonoFamiliare4;
	private String codiceFiscaleFamiliare5;
	private String numeroTelefonoFamiliare5;
	private String codiceFiscaleFamiliare6;
	private String numeroTelefonoFamiliare6;
	private String codiceFiscaleFamiliare7;
	private String numeroTelefonoFamiliare7;
	private String codiceFiscaleFamiliare8;
	private String numeroTelefonoFamiliare8;
	private String codiceFiscaleFamiliare9;
	private String numeroTelefonoFamiliare9;
	private String codiceFiscaleFamiliare10;
	private String numeroTelefonoFamiliare10;
	
	@JsonIgnore
	private Boolean colloquioCreato;
}
