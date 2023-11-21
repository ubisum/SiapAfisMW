package it.mgg.siapafismw.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FAMILIARE", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Familiare 
{
	@Id
	@Column(name = "TELEFONO")
	private String telefono;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "DOCUMENTO")
	private String documento;
	
	@Column(name = "NUMERO_DOCUMENTO")
	private String numeroDocumento;
	
	@Column(name = "GRADO_PARENTELA")
	private String gradoParentela;

    @Column(name = "DATA_DOCUMENTO")
	private String dataDocumento;
	
	@OneToMany(mappedBy = "familiare")
	private List<Allegato> listaAllegati;
	
	@ManyToMany(mappedBy = "familiari")
	private List<Detenuto> listaDetenuti;
}
