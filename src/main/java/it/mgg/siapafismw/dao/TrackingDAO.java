package it.mgg.siapafismw.dao;

import it.mgg.siapafismw.enums.EsitoTracking;
import it.mgg.siapafismw.enums.TrackingOperation;
import it.mgg.siapafismw.exceptions.SiapAfisMWException;

public interface TrackingDAO 
{
	public void storeTracking(TrackingOperation operation, Object data, EsitoTracking esito) throws SiapAfisMWException;
}
