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
public class SalvaFamiliareTrackingId implements Serializable
{
	private Integer ID_SOGGETTO;
	private Integer PROGRESSIVO_FAMILIARE;
	private Integer PROGRESSIVO_DOCUMENTO_FAMILIARE;
}
