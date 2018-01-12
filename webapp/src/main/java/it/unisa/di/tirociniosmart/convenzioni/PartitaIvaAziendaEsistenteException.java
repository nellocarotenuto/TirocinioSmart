package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando si tenta di inserire un'azienda la cui partita IVA è già presente
 * nel sistema.
 */
public class PartitaIvaAziendaEsistenteException extends Exception {

  private static final long serialVersionUID = 8794307163457723272L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Partita IVA già presente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public PartitaIvaAziendaEsistenteException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public PartitaIvaAziendaEsistenteException(String messaggio) {
    super(messaggio);
  }
  
}
