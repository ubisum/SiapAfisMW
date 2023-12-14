package it.mgg.siapafismw.model.tracking;

import java.time.LocalDate;
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
	private String nome;
	
	@Column(name = "COGNOME_FAMILIARE")
	private String cognome;
	
	@Column(name = "CODICE_FISCALE_FAMILIARE")
	private String codiceFiscale;
	
	@Column(name = "GRADO_PARENTELA")
	private String gradoParentela;
	
	@Column(name = "TELEFONO_FAMILIARE")
	private String telefono;
	
	@Column(name = "DOCUMENTO_FAMILIARE")
	private String documento;
	
	@Column(name = "NUMERO_DOCUMENTO_FAMILIARE")
	private String numeroDocumento;
	
	@Column(name = "DATA_DOCUMENTO_FAMILIARE")
	private LocalDate dataDocumento;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	@OneToMany(mappedBy = "salvaFamiliare")
	private List<SalvaFamiliareAllegatiTracking> listaAllegati;
		
}
