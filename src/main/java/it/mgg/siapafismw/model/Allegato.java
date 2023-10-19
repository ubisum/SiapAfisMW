package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ALLEGATO", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allegato 
{
	@Id
	@Column(name = "NOME_FILE")
	private String nomeFile;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "ALLEGATO_FAMILIARE")
	private Familiare familiare;
}