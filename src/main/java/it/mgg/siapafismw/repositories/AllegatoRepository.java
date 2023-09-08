package it.mgg.siapafismw.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.Allegato;

public interface AllegatoRepository extends JpaRepository<Allegato, String> 
{
	public List<Allegato> findByNomeFileIn(List<String> nomiFile);
}
