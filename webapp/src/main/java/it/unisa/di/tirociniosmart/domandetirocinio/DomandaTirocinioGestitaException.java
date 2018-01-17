package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando si fa riferimento ad una domanda di tirocinio già gestita.
 */
public class DomandaTirocinioGestitaException extends Exception {

  private static final long serialVersionUID = -6912026786478038224L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Domanda di tirocinio già gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DomandaTirocinioGestitaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DomandaTirocinioGestitaException(String messaggio) {
    super(messaggio);
  }
  
}


