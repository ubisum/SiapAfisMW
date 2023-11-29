package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.ColloquioFamiliareTMiddle;
import it.mgg.siapafismw.model.ColloquioFamiliareTMiddleId;

public interface ColloquioFamiliareTMIddleRepository extends JpaRepository<ColloquioFamiliareTMiddle, 
                                                                           ColloquioFamiliareTMiddleId> 
{
	@Query("select coll from ColloquioFamiliareTMiddle coll where "
			+ " coll.colloquioId.M303_MAT = :matricola"
			+ " and coll.colloquioId.M303_PRG = :progressivo")
	public ColloquioFamiliareTMiddle fidByMatricolaProgressivo(String matricola, Integer progressivo);
}
