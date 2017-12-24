package Impiegati;

import Utenza.UtenteRegistrato;

public class ImpiegatoUfficioTirocini extends UtenteRegistrato{

	public ImpiegatoUfficioTirocini() {
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

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	protected void finalize() throws Throwable {
		super.finalize();
	}

	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		return super.toString();
	}

}
