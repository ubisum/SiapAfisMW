package it.mgg.siapafismw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VDC_SALVA_FAMILIARE_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalvaFamiliareTracking 
{
	@EmbeddedId
	private SalvaFamiliareTrackingId salvaFamiliareTrackigId;
	
	@Column(name = "NOME_FAMILIARE")
	private String nomeFamiliare;
	
	@Column(name = "COGNOME_FAMILIARE")
	private String cognomeFamiliare;
	
	@Column(name = "CODICE_FISCALE_FAMILIARE")
	private String codiceFiscaleFamiliare;
	
	@Column(name = "RELAZIONE_PARENTELA")
	private String relazioneParentela;
	
	@Column(name = "UTENZA_FAMILIARE")
	private String utenzaFamiliare;
	
	@Column(name = "TIPO_DOCUMENTO_FAMILIARE")
	private Integer tipoDocumentoFamiliare;
	
	@Column(name = "NUMERO_DOCUMENTO_FAMILIARE")
	private String numeroDocumentoFamiliare;
	
	@Column(name = "DATA_DOCUMENTO_FAMILIARE")
	private LocalDate dataDocumentoFamiliare;
	
	@Column(name = "LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
}
