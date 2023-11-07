package it.mgg.siapafismw.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MDD00_MATRICOLA", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatricolaTMiddle 
{
	@Id
	@Column(name = "M00_MAT")
	private String matricola;
	
	@Column(name = "M00_DT_PRIMO_ING")
	private LocalDate dataPrimoIngresso;
	
	@Column(name = "M00_HH_PRIMO_ING")
	private LocalTime oraPrimoIngresso;
	
	@Column(name = "M00_C_SEZ_APP")
	private String sezioneAppartenenza;
	
	@Column(name = "M00_C_CSSA_APP")
	private String cCssaApp;
	
	@Column(name = "M00_ID_SOGG")
	private Integer idSoggetto;
	
	@Column(name = "M00_C_ST_GIUR")
	private Integer statoGiuridico;
	
	@Column(name = "M00_C_ST_ESEC")
	private Integer statoEsecutivo;
	
	@Column(name = "M00_C_ST_RISTR")
	private String stRistr;
	
	@Column(name = "M00_DT_CARC_CHIUSA")
	private LocalDate dataCarcChiusa;
	
	@Column(name = "M00_HH_CARC_CHIUSA")
	private LocalTime oraCarcChiusa;
	
	@Column(name = "M00_FG_COMPETENZA")
	private String fgCompetenza;
	
	@Column(name = "M00_COD_CONNESS")
	private String codiceConnessione;
	
	@Column(name = "M00_ID_TIPO_INC")
	private Integer idTipoInc;
	
	@Column(name = "M00_DT_APERT_INC")
	private LocalDate dataApertInc;
	
	@Column(name = "M00_DT_CHIUS_INC")
	private LocalDate dataChiusInc;
	
	@Column(name = "M00_MOT_CHIUS_INC")
	private Integer motChiusInc;
	
	@Column(name = "M00_NUM_PROTOCOLLO")
	private String numeroProtocollo;
	
	@Column(name = "M00_PRC_ESE")
	private Integer prcEse;
	
	@Column(name = "M00_PRC_SCARC")
	private Integer prcScarc;
	
	@Column(name = "M00_MTV_SCARC")
	private Integer mtvScarc;
	
	@Column(name = "M00_D_SCARC_DEF")
	private LocalDate dataSCarcDef;
	
	@Column(name = "M00_D_SCARC_PROV")
	private LocalDate dataScarcProv;
	
	@Column(name = "M00_PRC_MIS_SIC")
	private Integer prcMisSic;
	
	@Column(name = "M00_C_ST_SLIB_SDET")
	private String cStSlibSdet;
	
	@Column(name = "M00_D_SLIB_SDET")
	private LocalDate dataSlibSdet;
	
	@Column(name = "M00_DT_SCAD_TERM")
	private LocalDate dataScadTerm;
	
	@Column(name = "M00_NOTE")
	private String note;
	
	@Column(name = "M00_DT_CANC")
	private LocalDateTime dataCanc;
	
	@Column(name = "M00_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M00_DT_INS")
	private LocalDateTime dataIns;
	
	@Column(name = "M00_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M00_DT_MOD")
	private LocalDateTime dateMod;
}
