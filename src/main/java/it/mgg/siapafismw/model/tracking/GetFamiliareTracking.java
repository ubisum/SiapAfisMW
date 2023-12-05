package it.mgg.siapafismw.model.tracking;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VDC_GET_FAMILIARE_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFamiliareTracking 
{
	@Id
	@Column(name = "GET_FAMILIARE_TRACKING_ID")
	private Integer getFamiliareTrackingId;
	
	@Column(name = "NUM_TELEFONO")
	private String numTelefono;
	
	@Column(name = "CODICE_FISCALE")
	private String codiceFiscale;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	@Column(name = "ESITO")
	private String esito;
}
