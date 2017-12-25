package it.unisa.di.tirociniosmart.convenzioni;

import java.time.LocalDateTime;

/**
 * Classe che modella una richiesta di convenzionamento con il dipartimento.
 */
public class RichiestaConvenzionamento {

  /**
   * Costruisce un oggetto RichiestaConvenzionamento vuoto che dev'essere popolato tramite i metodi
   * setters.
   */
  public RichiestaConvenzionamento() {
  }
  
  /**
   * Permette di ottenere l'identificatore della richiesta di convenzionamento.
   * 
   * @return Long che rappresenta l'identificatore della richiesta di convenzionamento
   */
  public long getId() {
    return id;
  }
  
  /**
   * Permette di specificare l'identificatore della richiesta di convenzionamento.
   * 
   * @param id Long che rappresenta l'identificatore della richiesta di convenzionamento
   */
  public void setId(long id) {
    this.id = id;
  }
  
  /**
   * Permette di ottenere lo stato della richiesta di convenzionamento.
   * 
   * @return {@link RichiestaConvenzionamento#IN_ATTESA} se la richiesta è in attesa,
   *         {@link RichiestaConvenzionamento#APPROVATA} se la richiesta è approvata,
   *         {@link RichiestaConvenzionamento#RIFIUTATA} se la richiesta è rifiutata
   */
  public int getStatus() {
    return status;
  }
  
  /**
   * Permette di specificare lo stato della richiesta di convenzionamento.
   * 
   * @param status Intero che rappresenta lo stato che si vuole assegnare alla richiesta di
   *               convenzionamento
   *               
   * @pre status = {@link RichiestaConvenzionamento#IN_ATTESA} or
   *      status = {@link RichiestaConvenzionamento#APPROVATA} or
   *      status = {@link RichiestaConvenzionamento#RIFIUTATA}
   *      
   * @post getStatus() = status
   */
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
  
  public String getPartitaIvaAzienda() {
    return partitaIvaAzienda;
  }
  
  public void setPartitaIvaAzienda(String partitaIvaAzienda) {
    this.partitaIvaAzienda = partitaIvaAzienda;
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
  
  public boolean isAziendaSenzaBarriere() {
    return aziendaSenzaBarriere;
  }
  
  public void setAziendaSenzaBarriere(boolean aziendaSenzaBarriere) {
    this.aziendaSenzaBarriere = aziendaSenzaBarriere;
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
  
  private long id;
  private int status;
  private LocalDateTime dataRichiesta;
  private LocalDateTime dataGestione;
  private String partitaIvaAzienda;
  private String indirizzoAzienda;
  private String nomeAzienda;
  private String idAzienda;
  private boolean aziendaSenzaBarriere;
  private String usernameDelegato;
  private String passwordDelegato;
  private String nomeDelegato;
  private String cognomeDelegato;
  private String emailDelegato;
  private char sessoDelegato;
  private String telefonoDelegato;
  
  public static final int IN_ATTESA = 0;
  public static final int APPROVATA = 1;
  public static final int RIFIUTATA = 2;

}
