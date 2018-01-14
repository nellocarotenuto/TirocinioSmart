package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sul nome di un utente fallisce perché questo non
 * è valido oppure è nullo.
 */
public class NomeNonValidoException extends Exception {
  
  private static final long serialVersionUID = 7342436857930048127L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Nome non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public NomeNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public NomeNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
