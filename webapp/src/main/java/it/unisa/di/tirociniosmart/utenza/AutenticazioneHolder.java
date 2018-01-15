package it.unisa.di.tirociniosmart.utenza;

/**
 * Classe utilizzata a livello di business per tenere traccia dell'utente autenticato all'interno
 * del sistema. L'iniezione avviene tramite {@link AutenticazioneManager}.
 * 
 * @see UtenteRegistrato
 * @see AutenticazioneManager
 */
public class AutenticazioneHolder {
  
  private static ThreadLocal<UtenteRegistrato> utenteThreadLocal = new ThreadLocal<>();
  
  /**
   * Permette di aggiungere un utente alla sessione.
   * 
   * @param utente Oggetto {@link UtenteRegistrato} da aggiungere alla sessione
   */
  public static void setUtente(UtenteRegistrato utente) {
    if (utente != null) {
      utenteThreadLocal.set(utente);
    } else {
      utenteThreadLocal.remove();
    }
  }
  
  /**
   * Permette di ottenere l'utente autenticato.
   * 
   * @return L'{@link UtenteRegistrato} che rappresenta l'utente autenticato nel sistema,
   *         <b>null</b> se non vi è alcun utente in sessione
   */
  public static UtenteRegistrato getUtente() {
    return (UtenteRegistrato) utenteThreadLocal.get();
  }
  
}
