package it.unisa.di.tirociniosmart.progettiformativi;

/**
 * Eccezione lanciata quando si tenta di utilizzare un progetto formativo il cui identificatore non
 * è presente nel sistema.
 */
public class IdProgettoFormativoInesistenteException extends Exception {

  private static final long serialVersionUID = -4233914057337092277L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Progetto non esistente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IdProgettoFormativoInesistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IdProgettoFormativoInesistenteException(String messaggio) {
    super(messaggio);
  }
  
}
