package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando si tenta di inserire un'azienda il cui identificatore è già presente
 * nel sistema.
 */
public class IdAziendaEsistenteException extends Exception {

  private static final long serialVersionUID = 8776692110231469508L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Identificatore già presente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IdAziendaEsistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IdAziendaEsistenteException(String messaggio) {
    super(messaggio);
  }
  
}
