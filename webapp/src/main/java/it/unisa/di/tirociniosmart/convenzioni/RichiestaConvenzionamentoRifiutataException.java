package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando un delegato aziendale tenta di effettuare il login mentre la richiesta
 * di convenzionamento associata all'azienda che rappresenta è stata rifiutata.
 */
public class RichiestaConvenzionamentoRifiutataException
       extends RichiestaConvenzionamentoGestitaException {

  private static final long serialVersionUID = 3285140313419999571L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di convenzionamento rifiutata";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaConvenzionamentoRifiutataException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaConvenzionamentoRifiutataException(String messaggio) {
    super(messaggio);
  }
  
}
