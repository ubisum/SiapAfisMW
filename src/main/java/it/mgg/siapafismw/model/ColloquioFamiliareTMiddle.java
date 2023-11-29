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
@Table(name = "MDD303_COLLOQ_FAM", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColloquioFamiliareTMiddle 
{
	@EmbeddedId
	private ColloquioFamiliareTMiddleId colloquioId;
	
	@Column(name = "M303_C_SEZ_APP")
	private String cSezApp;
	
	@Column(name = "M303_CONT_MINORE")
	private String contMinore;
	
	@Column(name = "M303_DASOLO")
	private String daSolo;
	
	@Column(name = "M303_DATA")
	private LocalDate data;
	
	@Column(name = "M303_DT_CANC")
	private LocalDateTime dataCancellazione;
	
	@Column(name = "M303_DT_INS")
	private LocalDateTime dataInserimento;
	
	@Column(name = "M303_DT_MOD")
	private LocalDateTime dataModifica;
	
	@Column(name = "M303_HH_COLL_EFF")
	private Integer oreColloquioEffettive;
	
	@Column(name = "M303_HH_COLL_RICH")
	private Integer oreColloquioRichieste;
	
	@Column(name = "M303_ID_LUOGO")
	private Integer idLuogo;
	
	@Column(name = "M303_ID_MODALITA")
	private Integer idModalita;
	
	@Column(name = "M303_ID_SOGG")
	private Integer idSoggetto;
	
	@Column(name = "M303_INTERNO")
	private String interno;
	
	@Column(name = "M303_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M303_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M303_NOTE")
	private String note;
	
	@Column(name = "M303_PRES_MINORE")
	private String presMinore;
	
	@Column(name = "M303_PRG_FAM1")
	private Integer prgFam1;
	
	@Column(name = "M303_PRG_FAM2")
	private Integer prgFam2;
	
	@Column(name = "M303_PRG_FAM3")
	private Integer prgFam3;
	
	@Column(name = "M303_PRG_FAM4")
	private Integer prgFam4;
	
	@Column(name = "M303_PRG_FAM5")
	private Integer prgFam5;
	
	@Column(name = "M303_PRG_FAM6")
	private Integer prgFam6;
	
	@Column(name = "M303_PRG_FAM7")
	private Integer prgFam7;
	
	@Column(name = "M303_PRG_FAM8")
	private Integer prgFam8;
	
	@Column(name = "M303_PRG_FAM9")
	private Integer prgFam9;
	
	@Column(name = "M303_PRG_FAM10")
	private Integer prgFam10;
	
	@Column(name = "M303_PRG_TURNO")
	private Integer prgTurno;
	
	@Column(name = "M303_STATO")
	private String stato;
	
	@Column(name = "M303_TIPO")
	private String tipo;
	
	@Column(name = "M303_VETRO_DIV")
	private String vetroDivisorio;
	
	@Column(name = "M303_VIDEOCOLLOQUIO")
	private String videoColloquio;
}
