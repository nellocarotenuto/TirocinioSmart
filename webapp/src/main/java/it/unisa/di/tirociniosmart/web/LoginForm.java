package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi di un form di login HTML. Questo oggetto
 * viene passato come parametro ai controller dalla dispatcher servlet quando un utente sottomette
 * il modulo di login.
 */
public class LoginForm {
  
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
  
  private String username;
  private String password;
  
}
