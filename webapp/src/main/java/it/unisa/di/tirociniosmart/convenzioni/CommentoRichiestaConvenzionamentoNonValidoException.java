package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando si tenta di associare ad una richiesta di convenzionamento un commento
 * nullo o vuoto.
 */
public class CommentoRichiestaConvenzionamentoNonValidoException extends Exception {

  private static final long serialVersionUID = -3341469093182023509L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di convenzionamento già gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public CommentoRichiestaConvenzionamentoNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public CommentoRichiestaConvenzionamentoNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
