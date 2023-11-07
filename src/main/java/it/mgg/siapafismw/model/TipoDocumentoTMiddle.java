package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CSSC_TPDOC", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoTMiddle 
{
	@Id
	@Column(name = "M_IDTIPODOC")
	private Integer idTipoDocumento;
	
	@Column(name = "M_DESCRIZIONE")
	private String descrizione;
}
