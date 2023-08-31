package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DETENUTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detenuto 
{
	@Id
	@Column(name = "MATRICOLA")
	private String matricola;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "PENITENZIARIO")
	private String penitenziario;
	
	@Column(name = "SEZIONE")
	private String sezione;
}
