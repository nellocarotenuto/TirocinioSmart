package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sulla password di un utente fallisce perché questa non
 * rispetta il pattern oppure è nulla.
 */
public class PasswordNonValidaException extends Exception {

  private static final long serialVersionUID = -6604897505412217805L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Username non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public PasswordNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public PasswordNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}
