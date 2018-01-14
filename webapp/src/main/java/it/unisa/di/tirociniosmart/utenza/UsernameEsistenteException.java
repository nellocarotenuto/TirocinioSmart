package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sull'username di un utente fallisce perché questo è già
 * presente nel sistema.
 */
public class UsernameEsistenteException extends Exception {

  private static final long serialVersionUID = 6746818670147635153L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Username già presente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public UsernameEsistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public UsernameEsistenteException(String messaggio) {
    super(messaggio);
  }
  
}
