package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando lo stato di una domanda di tirocinio non è idoneo.
 */
public class StatoDomandaNonIdoneoException extends Exception {

  private static final long serialVersionUID = -4840475825509009651L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Stato domanda di tirocinio non idoneo";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public StatoDomandaNonIdoneoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public StatoDomandaNonIdoneoException(String messaggio) {
    super(messaggio);
  }
  
}

