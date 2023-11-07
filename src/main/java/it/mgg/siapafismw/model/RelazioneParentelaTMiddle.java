package it.mgg.siapafismw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MDC_RELPARENTELA", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelazioneParentelaTMiddle 
{
	@Id
	@Column(name = "IDRELPARENTELA")
	private String idParentela;
	
	@Column(name = "DESCRIZIONEREL")
	private String descrizioneParentela;
	
	@Column(name = "GRADOPARENTELA")
	private String gradoParentela;
}
