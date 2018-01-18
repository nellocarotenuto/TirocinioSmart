package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando un delegato aziendale tenta di effettuare il login mentre la richiesta
 * di convenzionamento associata all'azienda che rappresenta non è ancora stata gestita.
 */
public class RichiestaConvenzionamentoInAttesaException extends Exception {

  private static final long serialVersionUID = -4412904414535817775L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di convenzionamento non ancora gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaConvenzionamentoInAttesaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaConvenzionamentoInAttesaException(String messaggio) {
    super(messaggio);
  }
  
}
