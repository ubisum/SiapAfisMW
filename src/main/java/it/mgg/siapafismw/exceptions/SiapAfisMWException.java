package it.mgg.siapafismw.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SiapAfisMWException extends Exception 
{
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = -9205690631307705528L;
	
	private String message;
	private HttpStatus status;

}
