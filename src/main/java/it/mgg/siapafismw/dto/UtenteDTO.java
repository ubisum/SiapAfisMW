package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteDTO 
{
	private String username;
	private String password;
	private String nome;
	private String cognome;
	private String email;
}
