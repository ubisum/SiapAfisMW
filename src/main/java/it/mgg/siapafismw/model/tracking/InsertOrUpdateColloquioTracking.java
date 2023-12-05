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
@Table(name = "VDC_INSERT_OR_UPDATE_COLLOQUIO_TRACKING", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertOrUpdateColloquioTracking 
{
	@Id
	@Column(name = "VDC_INSERT_OR_UPDATE_COLLOQUIO_TRACKING_ID")
	private Integer insertOrUpdateColloquioTrackingId;
	
	@Column(name = "MATRICOLA")
	private String matricola;
	
	@Column(name = "ID_COLLOQUIO_DATAVERSE")
	private String idColloquioDataVerse;
	
	@Column(name = "DATA")
	private String data;
	
	@Column(name = "STATO")
	private String stato;
	
	@Column(name = "TIPO")
	private String tipo;
	
	@Column(name = "MODALITA")
	private String modalita;
	
	@Column(name = "ORE_RICHIESTE")
	private Integer oreRichieste;
	
	@Column(name = "ORE_EFFETTIVE")
	private Integer oreEffettive;
	
	@Column(name = "ORA_INIZIO_COLLOQUIO")
	private String oraInizioColloquio;
	
	@Column(name = "ORA_FINE_COLLOQUIO")
	private String oraFineColloquio;
	
	@Column(name = "COD_FIS_FAM_1")
	private String codiceFiscaleFamiliare1;
	
	@Column(name = "NUM_TEL_FAM_1")
	private String numeroTelefonoFamiliare1;
	
	@Column(name = "COD_FIS_FAM_2")
	private String codiceFiscaleFamiliare2;
	
	@Column(name = "NUM_TEL_FAM_2")
	private String numeroTelefonoFamiliare2;
	
	@Column(name = "COD_FIS_FAM_3")
	private String codiceFiscaleFamiliare3;
	
	@Column(name = "NUM_TEL_FAM_3")
	private String numeroTelefonoFamiliare3;
	
	@Column(name = "DATA_INSERIMENTO")
	private LocalDateTime dataInserimento;
	
	
}
