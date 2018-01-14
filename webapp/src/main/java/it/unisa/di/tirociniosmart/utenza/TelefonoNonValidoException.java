package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sul numero di telefono di un utente fallisce perché questo
 * è nullo o non rispetta il formato.
 */
public class TelefonoNonValidoException extends Exception {

  private static final long serialVersionUID = -1881795679633145760L;

  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Telefono non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public TelefonoNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public TelefonoNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
