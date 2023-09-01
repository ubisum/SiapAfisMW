package it.mgg.siapafismw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.mgg.siapafismw.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, String> {

}
