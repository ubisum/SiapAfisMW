package it.mgg.siapafismw.model.tracking;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Id
	@Column(name = "VDC_SALVA_FAMILIARE_TRACKING_ID")
	private Integer salvaFamiliareTrackingId;
	
	@Column(name = "NOME_FAMILIARE")
	private String nomeFamiliare;
	
	@Column(name = "COGNOME_FAMILIARE")
	private String cognomeFamiliare;
	
	@Column(name = "CODICE_FISCALE_FAMILIARE")
	private String codiceFiscaleFamiliare;
	
	@Column(name = "GRADO_PARENTELA")
	private String gradoParentela;
	
	@Column(name = "TELEFONO_FAMILIARE")
	private String telefonoFamiliare;
	
	@Column(name = "DOCUMENTO_FAMILIARE")
	private String documentoFamiliare;
	
	@Column(name = "NUMERO_DOCUMENTO_FAMILIARE")
	private String numeroDocumentoFamiliare;
	
	@Column(name = "DATA_DOCUMENTO_FAMILIARE")
	private String dataDocumentoFamiliare;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimennto;
	
	@OneToMany(mappedBy = "salvaFamiliare")
	private List<SalvaFamiliareAllegatiTracking> listaAllegati;
		
}
