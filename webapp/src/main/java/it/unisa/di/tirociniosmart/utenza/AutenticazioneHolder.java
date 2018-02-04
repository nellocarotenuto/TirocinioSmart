package it.unisa.di.tirociniosmart.utenza;

/**
 * Classe utilizzata per tenere traccia dell'utente autenticato all'interno del sistema. In pratica,
 * tramite questa classe è possibile specificare l'username dell'utente autenticato in maniera da
 * renderlo visibile a livello di thread.
 */
public class AutenticazioneHolder {
  
  private static ThreadLocal<String> utenteThreadLocal = new ThreadLocal<String>();
  
  /**
   * Permette di aggiungere l'username dell'utente autenticato in sessione al fine di renderlo
   * visibile a tutti i livelli.
   * 
   * @param username Stringa che rappresenta l'username dell'utente autenticato nel sistema
   */
  static void setUtente(String username) {
    if (username != null) {
      utenteThreadLocal.set(username);
    } else {
      utenteThreadLocal.remove();
    }
  }
  
  /**
   * Permette di ottenere l'username dell'utente autenticato nel sistema.
   * 
   * @return La stringa che rappresenta l'username dell'utente autenticato nel sistema,
   *         <b>null</b> se non vi è alcun utente in sessione
   */
  static String getUtente() {
    return utenteThreadLocal.get();
  }
  
}
