package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sull'email di un utente fallisce perché questa non
 * rispetta il pattern oppure è nulla.
 */
public class EmailNonValidaException extends Exception {

  private static final long serialVersionUID = 7088610255259067066L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "E-mail non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public EmailNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public EmailNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}
