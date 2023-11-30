package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.ColloquioTMiddle;
import it.mgg.siapafismw.model.ColloquioTMiddleId;

public interface ColloquioTMiddleRepository extends JpaRepository<ColloquioTMiddle, 
                                                                  ColloquioTMiddleId> 
{
	@Query("select coll from ColloquioTMiddle coll where coll.colloquioId.M321_MAT = :matricola"
			+ " and coll.colloquioId.M321_PRG_RICH = :progressivo")
	public ColloquioTMiddle findByMatricolaProgressivo(String matricola, Integer progressivo);
	
	@Query("select max(coll.colloquioId.M321_PRG_RICH) from ColloquioTMiddle coll "
			+ "where coll.colloquioId.M321_MAT = :matricola")
	public Integer getMaxProgressivoFromMatricola(String matricola);
	
	
}
