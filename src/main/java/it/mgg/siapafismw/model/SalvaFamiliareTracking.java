package it.mgg.siapafismw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SALVA_FAMILIARE_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalvaFamiliareTracking 
{
	@EmbeddedId
	private SalvaFamiliareTrackingId salvaFamiliareTrackigId;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
}
