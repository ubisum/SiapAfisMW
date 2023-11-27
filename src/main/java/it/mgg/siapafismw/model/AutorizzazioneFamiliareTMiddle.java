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
@Table(name = "MDD305_AUTOR_FAM", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorizzazioneFamiliareTMiddle 
{
	@EmbeddedId
	private AutorizzazioneFamiliareTMiddleId autorizzazioneFamiliareTMiddleId;
	
	@Column(name = "M305_LOGIN_INS")
	private Integer loginIns;
	
	@Column(name = "M305_DT_INS")
	private LocalDateTime dataInserimento;
	
	@Column(name = "M305_LOGIN_MOD")
	private Integer loginMod;
	
	@Column(name = "M305_DT_MOD")
	private LocalDateTime dataModifica;
	
	@Column(name = "M305_DT_CANC")
	private LocalDateTime dataCancellazione;
}
