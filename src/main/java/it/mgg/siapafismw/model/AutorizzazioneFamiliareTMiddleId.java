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
public class AutorizzazioneFamiliareTMiddleId implements Serializable
{
	private String M305_MAT;
	private Integer M305_PRG_AUTOR;
	private Integer M305_PRG_FAM;
}
