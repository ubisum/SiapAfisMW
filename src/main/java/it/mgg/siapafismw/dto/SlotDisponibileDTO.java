package it.mgg.siapafismw.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotDisponibileDTO 
{
	private String responseCode;
	private String respondeDescription;
	private String nome;
	private String cognome;
	private String matricola;
	private String penitenziario;
	private String sezione;
	private List<AvailabilityDTO> availability;
}
