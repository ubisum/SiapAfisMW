package it.mgg.siapafismw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.TipoDocumentoTMiddle;

public interface TipoDocumentoTMiddleRepository extends JpaRepository<TipoDocumentoTMiddle, Integer> 
{
	Optional<TipoDocumentoTMiddle> findByDescrizioneIgnoreCase(String descrizione);
}
