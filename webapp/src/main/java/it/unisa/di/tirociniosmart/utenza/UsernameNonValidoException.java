package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sull'username di un utente fallisce perché questo non
 * rispetta il pattern oppure è nullo.
 */
public class UsernameNonValidoException extends Exception {

  private static final long serialVersionUID = -8870973825876004160L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Username non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public UsernameNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public UsernameNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
