package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.SalvaFamiliareTracking;
import it.mgg.siapafismw.model.SalvaFamiliareTrackingId;

public interface SalvaFamiliareTrackingRepository extends JpaRepository<SalvaFamiliareTracking, SalvaFamiliareTrackingId> {

}
