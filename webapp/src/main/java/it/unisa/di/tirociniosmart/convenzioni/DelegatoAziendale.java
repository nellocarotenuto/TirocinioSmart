package Convenzioni;

import Utenza.UtenteRegistrato;

public class DelegatoAziendale extends UtenteRegistrato {

	public DelegatoAziendale() {
	}

	public String getUsername() {
		return super.getUsername();
	}

	public void setUsername(String username) {
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

	private char sesso;
	private String telefono;
}
