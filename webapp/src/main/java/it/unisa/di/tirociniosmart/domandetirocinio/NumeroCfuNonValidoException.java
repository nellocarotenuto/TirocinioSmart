package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando si tenta di inserire un numero di cfu che non rientra nell'intervallo
 * prestabilito.
 */
public class NumeroCfuNonValidoException extends Exception {

  private static final long serialVersionUID = -2326521333264778521L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Numero di cfu non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public NumeroCfuNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public NumeroCfuNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}

 