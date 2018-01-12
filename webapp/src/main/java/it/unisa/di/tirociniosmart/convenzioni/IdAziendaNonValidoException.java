package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando il controllo su un identificatore di un'azienda fallisce poiché questo
 * non rispetta il pattern o è nullo.
 */
public class IdAziendaNonValidoException extends Exception {

  private static final long serialVersionUID = 6155525815689375050L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Identificatore non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IdAziendaNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IdAziendaNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
