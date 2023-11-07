package it.mgg.siapafismw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.RelazioneParentelaTMiddle;

public interface RelazioneParentelaTMiddleRepository extends JpaRepository<RelazioneParentelaTMiddle, String> 
{
	Optional<RelazioneParentelaTMiddle> findByDescrizioneParentela(String parentela);
}
