package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando si fa riferimento ad una richiesta di convenzionamento già gestita.
 */
public class RichiestaConvenzionamentoGestitaException extends Exception {
  
  private static final long serialVersionUID = -5220806489819572756L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di convenzionamento già gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaConvenzionamentoGestitaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaConvenzionamentoGestitaException(String messaggio) {
    super(messaggio);
  }
  
}
