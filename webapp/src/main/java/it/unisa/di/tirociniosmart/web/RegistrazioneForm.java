package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi di un form di registrazione HTML. Questo oggetto
 * viene passato come parametro ai controller dalla dispatcher servlet quando un utente sottomette
 * il modulo di registrazione.
 */
public abstract class RegistrazioneForm {

  RegistrazioneForm() {
  }

  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getConfermaPassword() {
    return confermaPassword;
  }
  
  public void setConfermaPassword(String confermaPassword) {
    this.confermaPassword = confermaPassword;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
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

  protected String username;
  protected String password;
  protected String confermaPassword;
  protected String email;
  protected String nome;
  protected String cognome;
  
}
