package it.mgg.siapafismw.model;

import java.io.Serializable;

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
public class FamiliareTMiddleId implements Serializable
{
	private Integer M301_ID_SOGG;
	private Integer M301_PRG_FAM;
	
}
