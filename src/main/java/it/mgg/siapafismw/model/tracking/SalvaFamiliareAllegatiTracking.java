package it.mgg.siapafismw.model.tracking;

import java.time.LocalDateTime;

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
@Table(name = "VDC_SALVA_FAMILIARE_ALLEGATI_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalvaFamiliareAllegatiTracking 
{
	@Id
	@Column(name = "VDC_SALVA_FAMILIARE_ALLEGATI_TRACKING_ID")
	private Integer salvaFamiliareeAllegatiId;
	
	@Column(name = "NOME_FILE")
	private String nomeFile;
	
	@Column(name = "TIPO_FILE")
	private String tipoFile;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	@ManyToOne
	@JoinColumn(name = "SALVA_FAMILIARE_ID")
	private SalvaFamiliareTracking salvaFamiliare;
	
}
