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
@Table(name = "MDD304_AUTORIZZ", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorizzazioneTMiddle 
{
	@EmbeddedId
	private AutorizzazioneTMiddleId autorizzazioneTMiddleId;
	
	@Column(name = "M304_FUNZ")
	private String funzione;
	
	@Column(name = "M304_DATA")
	private LocalDate data;
	
	@Column(name = "M304_NUM")
	private String numero;
	
	@Column(name = "M304_DIR_AUTOR")
	private String dirAutor;
	
	@Column(name = "M304_ID_AUT_AUTOR")
	private Integer idDirAutor;
	
	@Column(name = "M304_TIPO")
	private String tipo;
	
	@Column(name = "M304_STATO")
	private String stato;
	
	@Column(name = "M304_ID_SOGG")
	private Integer idSoggetto;
	
	@Column(name = "M304_NOM_GIUDICE")
	private String nomGiudice;
	
	@Column(name = "M304_NUM_REATO")
	private String numReato; 
	
	@Column(name = "M304_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M304_DT_INS")
	private LocalDateTime dataInserimento;
	
	@Column(name = "M304_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M304_DT_MOD")
	private LocalDateTime dataModifica;
	
	@Column(name = "M304_DT_CANC")
	private LocalDateTime dataCancellazione;
}
