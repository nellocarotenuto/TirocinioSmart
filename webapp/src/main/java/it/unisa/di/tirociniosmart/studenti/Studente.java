package it.unisa.di.tirociniosmart.studenti;

import java.time.LocalDateTime;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

public class Studente extends UtenteRegistrato {
	
	public Studente() {
	}
	
	
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getUsername();
	}
	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		super.setUsername(username);
	}
	
	public String getPassword() {
		return super.getPassword();
	}
	
	public void setPassword(String password) {
		super.setPassword(password);
	}
	
	public String getNome() {
		return super.getNome();
	}
	
	public void setNome(String nome) {
		super.setNome(nome);
	}
	
	public String getCognome() {
		return super.getCognome();
	}
	
	public void setCognome(String cognome) {
		super.setCognome(cognome);
	}
	
	public String getEmail() {
		return super.getEmail();
	}
	
	public void setEmail(String email) {
		super.setEmail(email);
	}

	public LocalDateTime getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(LocalDateTime dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public LocalDateTime getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(LocalDateTime dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public char getSesso() {
		return sesso;
	}

	public void setSesso(char sesso) {
		this.sesso = sesso;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	private LocalDateTime dataRegistrazione;
	private String matricola;
	private String indirizzo;
	private LocalDateTime dataDiNascita;
	private char sesso;
	private String telefono;
}
