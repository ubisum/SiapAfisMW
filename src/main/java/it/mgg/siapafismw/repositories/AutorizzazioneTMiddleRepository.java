package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.AutorizzazioneTMiddle;
import it.mgg.siapafismw.model.AutorizzazioneTMiddleId;

public interface AutorizzazioneTMiddleRepository extends JpaRepository<AutorizzazioneTMiddle, AutorizzazioneTMiddleId> 
{
	@Query("select max(a.autorizzazioneTMiddleId.M304_PRG) from AutorizzazioneTMiddle a "
			+ "where a.autorizzazioneTMiddleId.M304_MAT = :matricola")
	public Integer findMaxPrgByMatricola(String matricola);
}
