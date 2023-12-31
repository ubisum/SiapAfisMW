package it.mgg.siapafismw.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ColloquioTMiddleId implements Serializable 
{
	private String M321_MAT;
	private Integer M321_PRG_RICH;
	private Integer M321_PRG_SOST;
	private LocalDateTime M321_DATA_ORA;
}
