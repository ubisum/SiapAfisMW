package it.mgg.siapafismw.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MDC_TIPODOCUMENTO", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoTMiddle 
{
	@Id
	@Column(name = "ID_TIPO_DOC")
	private Integer idTipoDocumento;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@Column(name = "DESCRBREVE")
	private String descrizioneBreve;
	
	@Column(name = "DATAFINEVALIDITA")
	private LocalDate dataFineValidita; 
}
