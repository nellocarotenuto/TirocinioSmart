package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando il controllo su un delegato di un'azienda fallisce poiché questo
 * è nullo.
 */
public class DelegatoNonValidoException extends Exception {
  
  private static final long serialVersionUID = 3436988408661918683L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Identificatore non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DelegatoNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DelegatoNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
