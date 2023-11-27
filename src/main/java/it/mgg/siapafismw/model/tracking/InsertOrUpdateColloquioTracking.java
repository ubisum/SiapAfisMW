package it.mgg.siapafismw.model.tracking;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VDC_INSERT_OR_UPDATE_COLLOQUIO_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertOrUpdateColloquioTracking 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INSERT_OR_UPDATE_COLLOQUIO_TRACKING_ID")
	private Integer insertOrUpdateColloquioTrackingId;
	
	@Column(name = "COLLOQUIO_ID")
	private String colloquioId;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	@Column(name = "ESITO")
	private String esito;
}
