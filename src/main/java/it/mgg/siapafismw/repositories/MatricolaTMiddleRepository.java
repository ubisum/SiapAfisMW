package it.mgg.siapafismw.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.mgg.siapafismw.model.MatricolaTMiddle;

public interface MatricolaTMiddleRepository extends JpaRepository<MatricolaTMiddle, String> 
{
	@Query("select mat from MatricolaTMiddle mat where mat.matricola = :matricola "
		 + "and mat.dataCanc is null")
	public Optional<MatricolaTMiddle> findByIdDtCancNotNull(String matricola);
}
