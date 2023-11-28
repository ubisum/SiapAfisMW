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
@Table(name = "VDC_GET_INFO_DETENUTO_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInfoDetenutoTracking 
{
	@Id
	@Column(name = "GET_INFO_DETENUTO_TRACKING_ID")
	private Integer getInfoDetenutoTrackingId;
	
	@Column(name = "MATRICOLA")
	private String matricola;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	@Column(name = "ESITO")
	private String esito;
}
