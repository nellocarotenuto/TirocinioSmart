package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando il controllo su una matricola di un'azienda fallisce poiché questa
 * non rispetta il pattern o è nulla.
 */
public class MatricolaStudenteNonValidaException extends Exception {

  private static final long serialVersionUID = 1L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = " MATRICOLA non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public MatricolaStudenteNonValidaException() {
    super(messaggioDefault);   
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public MatricolaStudenteNonValidaException(String messaggio) {
    super(messaggio);
  }
}
