package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.DocumentoTMiddle;
import it.mgg.siapafismw.model.DocumentoTMiddleId;

public interface DocumentoTMiddleRepository extends JpaRepository<DocumentoTMiddle, DocumentoTMiddleId> 
{
	@Query("select max(d.documentoId.M302_PRG_DOC) from DocumentoTMiddle d "
		 + "where d.documentoId.M302_ID_SOGG = :idSoggetto and d.documentoId.M302_PRG_FAM = :progFamiliare")
	public Integer getMaxProgressivoDocumento(Integer idSoggetto, Integer progFamiliare);
}
