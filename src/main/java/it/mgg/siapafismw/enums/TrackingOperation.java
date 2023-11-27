package it.mgg.siapafismw.enums;

public enum TrackingOperation 
{
	SALVA_FAMILIARE("SALVA_FAMILIARE"),
	GET_FAMILIARE("GET_FAMILIARE"),
	GET_INFO_DETENUTO("GET_INFO_DETENUTO"),
	GET_LISTA_DETENUTI("GET_LISTA_DETENUTI"),
	INSERT_UPDATE_COLLOQUIO("INSERT_UPDATE_COLLOQUIO");
	
	private String operation;

	private TrackingOperation(String operation) {
		this.operation = operation;
	}

	public String getOperation() {
		return operation;
	}
}
