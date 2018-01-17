package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando si tenta di associare la domanda di tirocinio ad un progetto
 * formativo archiviato.
 */
public class ProgettoFormativoArchiviatoException extends Exception {
  
  private static final long serialVersionUID = 4164231866454390274L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Progetto formativo archiviato";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public ProgettoFormativoArchiviatoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public ProgettoFormativoArchiviatoException(String messaggio) {
    super(messaggio);
  }
  
}
