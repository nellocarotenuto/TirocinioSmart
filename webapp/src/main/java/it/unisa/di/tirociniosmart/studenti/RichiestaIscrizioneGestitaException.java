package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando si fa riferimento ad una richiesta di iscrizione già gestita ovvero che
 * è stata già approvata o rifiutata.
 */
public class RichiestaIscrizioneGestitaException extends Exception {
  
  private static final long serialVersionUID = 1859963191504258483L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di iscrizione già gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaIscrizioneGestitaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaIscrizioneGestitaException(String messaggio) {
    super(messaggio);
  }
  
}

