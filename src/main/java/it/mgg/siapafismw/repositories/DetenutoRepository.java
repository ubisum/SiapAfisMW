package it.mgg.siapafismw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.Detenuto;

public interface DetenutoRepository extends JpaRepository<Detenuto, String> 
{
	public Optional<Detenuto> findById(String matricola);
}
