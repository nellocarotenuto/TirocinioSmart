package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando si tenta di inserire uno studente la cui matricola è già presente
 * nel sistema.
 */
public class MatricolaStudenteEsistenteException extends Exception {

  private static final long serialVersionUID = -4949499670367427007L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Matricola già presente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public MatricolaStudenteEsistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public MatricolaStudenteEsistenteException(String messaggio) {
    super(messaggio);
  }
  
}

