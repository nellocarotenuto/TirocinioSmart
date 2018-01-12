package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando il controllo sul nome IVA di un'azienda fallisce poiché questo
 * non rispetta il pattern o è nullo.
 */
public class NomeAziendaNonValidoException extends Exception {

  private static final long serialVersionUID = -5871131123082952550L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Partita IVA non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public NomeAziendaNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public NomeAziendaNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
