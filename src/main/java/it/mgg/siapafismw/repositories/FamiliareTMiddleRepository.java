package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.FamiliareTMiddle;
import it.mgg.siapafismw.model.FamiliareTMiddleId;

public interface FamiliareTMiddleRepository extends JpaRepository<FamiliareTMiddle, FamiliareTMiddleId> 
{
	@Query("select max(f.familiareId.M301_PRG_FAM) from FamiliareTMiddle f where f.familiareId.M301_ID_SOGG = :idSoggetto")
	public Integer getMaxPrgFamiliare(Integer idSoggetto);
	
	@Query("select max(f.familiareId.M301_PRG_FAM) from FamiliareTMiddle f where f.familiareId.M301_ID_SOGG = :idSoggetto and "
		 + "(f.utenza = :utenza or f.codiceFiscale = :codiceFiscale)")
	public Integer getProgressivoFamiliareAssociato(Integer idSoggetto, String utenza, String codiceFiscale);
}
