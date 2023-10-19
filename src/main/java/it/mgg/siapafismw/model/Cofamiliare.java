package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COFAMILIARE", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cofamiliare 
{
	@Id
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "GRADO_PARENTELA")
	private String gradoParentela;
	
}
