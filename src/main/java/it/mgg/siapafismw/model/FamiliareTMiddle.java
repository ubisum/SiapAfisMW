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
@Table(name = "MDD301_FAMILIARE", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamiliareTMiddle 
{
	@EmbeddedId
	private FamiliareTMiddleId familiareId;
	
	@Column(name = "M301_COGNOME")
	private String cognome;

	@Column(name = "M301_NOME")
	private String nome;
	
	@Column(name = "M301_DT_NAS")
	private LocalDate dataNascita;
	
	@Column(name = "M301_ID_STATO_NASC")
	private String idStatoNascita;
	
	@Column(name = "M301_ID_COMUNE_NAS")
	private String idComuneNascita;
	
	@Column(name = "M301_LG_ESTERO_NAS")
	private String lgEsteroNascita;
	
	@Column(name = "M301_ID_STATO_RES")
	private String idStatoResideza;
	
	@Column(name = "M301_ID_COMUNE_RES")
	private String idComuneResidenza;
	
	@Column(name = "M301_LG_ESTERO_RES")
	private String lgEsteroResidenza;
	
	@Column(name = "M301_REL_PAR")
	private String relazioneParentela;
	
	@Column(name = "M301_CERTIFICAZ")
	private String certificazione;
	
	@Column(name = "M301_DT_CANC")
	private LocalDateTime dataCancellazione;
	
	@Column(name = "M301_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M301_DT_INS")
	private LocalDateTime dataInserimento;
	
	@Column(name = "M301_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M301_DT_MOD")
	private LocalDateTime dataModifica;
	
	@Column(name = "M301_UTENZA")
	private String utenza;
	
	@Column(name = "M301_STATO")
	private String stato;
	
	@Column(name = "M301_ATTIVITA")
	private String attivita;
	
	@Column(name = "M301_NOTE")
	private String note;
	
	@Column(name = "M301_TIPO_FAM")
	private String tipoFamiliare;
	
	@Column(name = "M301_COD_FISCALE")
	private String codiceFiscale;
}
