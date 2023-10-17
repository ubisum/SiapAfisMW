package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO 
{
	private String username;
	private String cognome;
	private String nome;
	private String email;
	private String password;
}
