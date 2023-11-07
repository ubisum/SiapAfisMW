package it.mgg.siapafismw.model;

import java.io.Serializable;
import java.time.LocalDate;

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
public class DocumentoTMiddleId implements Serializable
{
	private Integer M302_ID_SOGG;
	private Integer M302_PRG_FAM;
	private Integer M302_PRG_DOC;
	private Integer M302_TIPO_DOC;
	private String M302_NUM_DOC;
	private LocalDate M302_DT_DOC;
}
