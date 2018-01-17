package it.unisa.di.tirociniosmart.domandetirocinio;

/**
 * Eccezione lanciata quando il commento ad una domanda di tirocinio non è valido poiché nullo o
 * vuoto.
 */
public class CommentoDomandaTirocinioNonValidoException extends Exception {

  private static final long serialVersionUID = 5597318036587224602L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Commento non valido per la domanda";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public CommentoDomandaTirocinioNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public CommentoDomandaTirocinioNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}


