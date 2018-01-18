package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando uno studente tenta di effettuare il login mentre la sua richiesta
 * d'iscrizione è stata rifiutata.
 */
public class RichiestaIscrizioneRifiutataException
       extends RichiestaIscrizioneGestitaException {

  private static final long serialVersionUID = -7276697998198013271L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Richiesta d'iscrizione rifiutata";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public RichiestaIscrizioneRifiutataException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public RichiestaIscrizioneRifiutataException(String messaggio) {
    super(messaggio);
  }
  
}
