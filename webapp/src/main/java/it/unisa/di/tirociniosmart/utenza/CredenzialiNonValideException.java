package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando il login fallisce poiché le credenziali inserite non sono presenti nel 
 * sistema.
 */
public class CredenzialiNonValideException extends Exception {

  private static final long serialVersionUID = -3212124280736230614L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Credenziali non valide";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public CredenzialiNonValideException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public CredenzialiNonValideException(String messaggio) {
    super(messaggio);
  }
  
}