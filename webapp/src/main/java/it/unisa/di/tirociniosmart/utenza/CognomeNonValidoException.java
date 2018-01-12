package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il controllo sul cognome di un utente fallisce perché questo non
 * è valido oppure è nullo.
 */
public class CognomeNonValidoException extends Exception {

  private static final long serialVersionUID = 2341633524001032349L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Cognome non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public CognomeNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public CognomeNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
