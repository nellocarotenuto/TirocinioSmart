package Convenzioni;

import java.time.LocalDateTime;

public class RichiestaConvenzionamento {

	public RichiestaConvenzionamento() {
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
	public String getPartitaIVAAzienda() {
		return partitaIVAAzienda;
	}
	public void setPartitaIVAAzienda(String partitaIVAAzienda) {
		this.partitaIVAAzienda = partitaIVAAzienda;
	}
	public String getIndirizzoAzienda() {
		return indirizzoAzienda;
	}
	public void setIndirizzoAzienda(String indirizzoAzienda) {
		this.indirizzoAzienda = indirizzoAzienda;
	}
	public String getNomeAzienda() {
		return nomeAzienda;
	}
	public void setNomeAzienda(String nomeAzienda) {
		this.nomeAzienda = nomeAzienda;
	}
	public String getIdAzienda() {
		return idAzienda;
	}
	public void setIdAzienda(String idAzienda) {
		this.idAzienda = idAzienda;
	}
	public int getAccessibilitàAzienda() {
		return accessibilitàAzienda;
	}
	public void setAccessibilitàAzienda(int accessibilitàAzienda) {
		this.accessibilitàAzienda = accessibilitàAzienda;
	}
	public String getUsernameDelegato() {
		return usernameDelegato;
	}
	public void setUsernameDelegato(String usernameDelegato) {
		this.usernameDelegato = usernameDelegato;
	}
	public String getPasswordDelegato() {
		return passwordDelegato;
	}
	public void setPasswordDelegato(String passwordDelegato) {
		this.passwordDelegato = passwordDelegato;
	}
	public String getNomeDelegato() {
		return nomeDelegato;
	}
	public void setNomeDelegato(String nomeDelegato) {
		this.nomeDelegato = nomeDelegato;
	}
	public String getCognomeDelegato() {
		return cognomeDelegato;
	}
	public void setCognomeDelegato(String cognomeDelegato) {
		this.cognomeDelegato = cognomeDelegato;
	}
	public String getEmailDelegato() {
		return emailDelegato;
	}
	public void setEmailDelegato(String emailDelegato) {
		this.emailDelegato = emailDelegato;
	}
	public char getSessoDelegato() {
		return sessoDelegato;
	}
	public void setSessoDelegato(char sessoDelegato) {
		this.sessoDelegato = sessoDelegato;
	}
	public String getTelefonoDelegato() {
		return telefonoDelegato;
	}
	public void setTelefonoDelegato(String telefonoDelegato) {
		this.telefonoDelegato = telefonoDelegato;
	}




	private int id;
	private int status;
	private LocalDateTime dataRichiesta;
	private LocalDateTime dataGestione;
	private String partitaIVAAzienda;
	private String indirizzoAzienda;
	private String nomeAzienda;
	private String idAzienda;
	private int accessibilitàAzienda;
	private String usernameDelegato;
	private String passwordDelegato;
	private String nomeDelegato;
	private String cognomeDelegato;
	private String emailDelegato;
	private char sessoDelegato;
	private String telefonoDelegato;
}
