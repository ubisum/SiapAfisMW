package it.mgg.siapafismw.model;

import it.mgg.siapafismw.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UTENTE", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utente 
{
	@Id
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PW")
	private String password;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Enumerated(EnumType.STRING)
    private Role role;
}
