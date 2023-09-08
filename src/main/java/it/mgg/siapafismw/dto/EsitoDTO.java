package it.mgg.siapafismw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsitoDTO 
{
	private String responseCode;
	private String responseDescription;
}
