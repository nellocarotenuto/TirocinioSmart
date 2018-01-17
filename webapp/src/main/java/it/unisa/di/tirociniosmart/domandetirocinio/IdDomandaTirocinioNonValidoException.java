package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando si fa riferimento ad una domanda di tirocinio con un
 * identificatore inesistente.
 */
public class IdDomandaTirocinioNonValidoException extends Exception {

  private static final long serialVersionUID = 1955446352513806934L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Domanda di tirocinio inesistente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IdDomandaTirocinioNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IdDomandaTirocinioNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}