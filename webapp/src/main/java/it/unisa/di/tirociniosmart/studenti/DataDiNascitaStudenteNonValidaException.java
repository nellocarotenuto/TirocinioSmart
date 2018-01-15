package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando si tenta di inserire uno studente la cui data di nascita non è valida
 * o non rientra nell'intervallo prestabilito.
 */
public class DataDiNascitaStudenteNonValidaException extends Exception {
  
  private static final long serialVersionUID = -6457566332457096810L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Data di nascita non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DataDiNascitaStudenteNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DataDiNascitaStudenteNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}
