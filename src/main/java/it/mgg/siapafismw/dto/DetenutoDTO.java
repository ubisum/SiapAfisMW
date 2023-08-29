package it.mgg.siapafismw.dto;

public class DetenutoDTO 
{
	private String nome;
	private String cognome;
	private String matricola;
	private String penitenziario;
	private String sezione;
	
	public DetenutoDTO(String nome, String cognome, String matricola, String penitenziario, String sezione) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.matricola = matricola;
		this.penitenziario = penitenziario;
		this.sezione = sezione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getPenitenziario() {
		return penitenziario;
	}

	public void setPenitenziario(String penitenziario) {
		this.penitenziario = penitenziario;
	}

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
	
}
