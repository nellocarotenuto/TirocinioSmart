package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando si tenta di inserire una data di inzio tirocinio che non è valida
 * o non rientra nell'intervallo prestabilito.
 */
public class DataDiInizioTirocinioNonValidaException extends Exception {
  
  private static final long serialVersionUID = -3939575241309593054L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Data di inizio tirocinio non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DataDiInizioTirocinioNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DataDiInizioTirocinioNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}

