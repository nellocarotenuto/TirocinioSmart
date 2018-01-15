package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando si fa riferimento ad una richiesta di iscrizione con un
 * identificatore inesistente.
 *
 */
public class IdRichiestaIscrizioneNonValidoException extends Exception {
  
  private static final long serialVersionUID = 8226875016617974255L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta di convenzionamento inesistente";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IdRichiestaIscrizioneNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IdRichiestaIscrizioneNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}

