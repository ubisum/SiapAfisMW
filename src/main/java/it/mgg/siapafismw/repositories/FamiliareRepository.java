package it.mgg.siapafismw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.Familiare;

public interface FamiliareRepository extends JpaRepository<Familiare, String> 
{
	public Optional<Familiare> findByTelefono(String telefono);
}
