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
@Table(name = "MDD302_DOCUMENTO", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoTMiddle 
{
	@EmbeddedId
	private DocumentoTMiddleId documentoId;
	
	@Column(name = "M302_UFF_RILASCIO")
	private String ufficioRilascio;
	
	@Column(name = "M302_DT_CANC")
	private LocalDateTime dataCancellazione;
	
	@Column(name = "M302_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M302_DT_INS")
	private LocalDateTime dataIns;
	
	@Column(name = "M302_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M302_DT_MOD")
	private LocalDateTime dataMod;
}
