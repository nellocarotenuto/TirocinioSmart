package it.unisa.di.tirociniosmart.utenza;

/**
 * Eccezione lanciata quando l'utente non è autorizzato ad eseguire una certa operazione.
 */
public class RichiestaNonAutorizzataException extends Exception {

  private static final long serialVersionUID = -2056102371884153299L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta non autorizzata";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaNonAutorizzataException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaNonAutorizzataException(String messaggio) {
    super(messaggio);
  }
  
}
