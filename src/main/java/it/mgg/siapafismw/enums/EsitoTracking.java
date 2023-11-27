package it.mgg.siapafismw.enums;

public enum EsitoTracking 
{
	OK("OK"),
	KO("KO");
	
	private String esitoTracking;

	private EsitoTracking(String esitoTracking) {
		this.esitoTracking = esitoTracking;
	}

	public String getEsitoTracking() {
		return esitoTracking;
	}
	
	
}
