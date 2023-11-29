package it.mgg.siapafismw.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MDD321_COLLOQUIO", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColloquioTMiddle 
{
	@EmbeddedId
	private ColloquioTMiddleId colloquioId;
	
	@Column(name = "M321_PRG_SOST")
	private Integer prgSost;
	
	@Column(name = "M321_DATA_ORA")
	private LocalDateTime dataOra;
	
	@Column(name = "M321_ID_INTERPR")
	private Integer idInterpr;
	
	@Column(name = "M321_STATO")
	private String stato;
	
	@Column(name = "M321_DT_CANC")
	private LocalDateTime dataCancellazione;
	
	@Column(name = "M321_LOGIN_INS")
	private Integer loginIs;
	
	@Column(name = "M321_DT_INS")
	private LocalDateTime dataInserimento;
	
	@Column(name = "M321_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M321_DT_MOD")
	private LocalDateTime dataModifica;
	
	@Column(name = "M321_HH_INIZIO")
	private LocalTime hhInizio;
	
	@Column(name = "M321_HH_FINE")
	private LocalTime hhFine;
	
	@Column(name = "M321_FG_AVV")
	private Integer fgAvv;
	
	@Column(name = "M321_FG_SOST")
	private Integer fgSost;
}
