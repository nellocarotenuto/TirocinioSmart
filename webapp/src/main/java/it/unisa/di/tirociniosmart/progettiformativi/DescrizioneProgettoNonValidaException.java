package it.unisa.di.tirociniosmart.progettiformativi;

/**
 * Eccezione lanciata quando il controllo sulla descrizione di un progetto fallisce perché questo 
 * non è valida oppure è nulla.
 */
public class DescrizioneProgettoNonValidaException extends Exception {
  
  private static final long serialVersionUID = 2558277770822707384L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Descrizione non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DescrizioneProgettoNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DescrizioneProgettoNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}