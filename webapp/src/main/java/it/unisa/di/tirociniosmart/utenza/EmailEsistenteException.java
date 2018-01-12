package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sull'email di un utente fallisce perché questa è già
 * presente nel sistema.
 */
public class EmailEsistenteException extends Exception {
  
  private static final long serialVersionUID = 1026733380479543860L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "E-mail già presente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public EmailEsistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public EmailEsistenteException(String messaggio) {
    super(messaggio);
  }
  
}
