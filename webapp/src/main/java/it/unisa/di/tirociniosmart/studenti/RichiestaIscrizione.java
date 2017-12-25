package it.unisa.di.tirociniosmart.studenti;

import java.time.LocalDateTime;

public class RichiestaIscrizione {

	public RichiestaIscrizione() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDateTime getDataRichiesta() {
		return dataRichiesta;
	}
	public void setDataRichiesta(LocalDateTime dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}
	public LocalDateTime getDataGestione() {
		return dataGestione;
	}
	public void setDataGestione(LocalDateTime dataGestione) {
		this.dataGestione = dataGestione;
	}
	public String getUsernameStudente() {
		return usernameStudente;
	}
	public void setUsernameStudente(String usernameStudente) {
		this.usernameStudente = usernameStudente;
	}
	public String getPasswordStudente() {
		return passwordStudente;
	}
	public void setPasswordStudente(String passwordStudente) {
		this.passwordStudente = passwordStudente;
	}
	public String getNomeStudente() {
		return nomeStudente;
	}
	public void setNomeStudente(String nomeStudente) {
		this.nomeStudente = nomeStudente;
	}
	public String getCognomeStudente() {
		return cognomeStudente;
	}
	public void setCognomeStudente(String cognomeStudente) {
		this.cognomeStudente = cognomeStudente;
	}
	public String getEmailStudente() {
		return emailStudente;
	}
	public void setEmailStudente(String emailStudente) {
		this.emailStudente = emailStudente;
	}
	public String getMatricolaStudente() {
		return matricolaStudente;
	}
	public void setMatricolaStudente(String matricolaStudente) {
		this.matricolaStudente = matricolaStudente;
	}
	public String getIndirizzoStudente() {
		return indirizzoStudente;
	}
	public void setIndirizzoStudente(String indirizzoStudente) {
		this.indirizzoStudente = indirizzoStudente;
	}
	public LocalDateTime getDataDiNascitaStudente() {
		return dataDiNascitaStudente;
	}
	public void setDataDiNascitaStudente(LocalDateTime dataDiNascitaStudente) {
		this.dataDiNascitaStudente = dataDiNascitaStudente;
	}
	public char getSessoStudente() {
		return sessoStudente;
	}
	public void setSessoStudente(char sessoStudente) {
		this.sessoStudente = sessoStudente;
	}
	public String getTelefonoStudente() {
		return telefonoStudente;
	}
	public void setTelefonoStudente(String telefonoStudente) {
		this.telefonoStudente = telefonoStudente;
	}

	private int id;
	private int status;
	private LocalDateTime dataRichiesta;
	private LocalDateTime dataGestione;
	private String usernameStudente;
	private String passwordStudente;
	private String nomeStudente;
	private String cognomeStudente;
	private String emailStudente;
	private String matricolaStudente;
	private String indirizzoStudente;
	private LocalDateTime dataDiNascitaStudente;
	private char sessoStudente;
	private String telefonoStudente;
	
}
