package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando uno studente tenta di effettuare il login mentre la sua richiesta
 * d'iscrizione non è ancora stata gestita.
 */
public class RichiestaIscrizioneInAttesaException extends Exception {

  private static final long serialVersionUID = -4080459014921377909L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta d'iscrizione non ancora gestita";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaIscrizioneInAttesaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaIscrizioneInAttesaException(String messaggio) {
    super(messaggio);
  }
  
}
