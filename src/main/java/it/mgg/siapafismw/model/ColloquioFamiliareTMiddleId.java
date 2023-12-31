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
public class ColloquioFamiliareTMiddleId implements Serializable 
{
	private String M303_MAT;
	private Integer M303_PRG;
	
}
