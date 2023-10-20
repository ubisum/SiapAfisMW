package it.mgg.siapafismw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DETENUTO", schema = "GATEWAY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Detenuto 
{
	@Id
	@Column(name = "MATRICOLA")
	private String matricola;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "COGNOME")
	private String cognome;
	
	@Column(name = "PENITENZIARIO")
	private String penitenziario;
	
	@Column(name = "SEZIONE")
	private String sezione;
	
	@ManyToMany
    @JoinTable(
        name = "FAMILIARE_DETENUTO", schema = "GATEWAY",
        joinColumns = { @JoinColumn(name = "ID_DETENUTO") }, 
        inverseJoinColumns = { @JoinColumn(name = "ID_FAMILIARE")})
    private List<Familiare> familiari = new ArrayList<>();
}
