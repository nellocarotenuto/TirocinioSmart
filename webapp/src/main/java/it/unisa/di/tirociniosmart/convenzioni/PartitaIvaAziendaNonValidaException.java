package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando il controllo su una partita IVA di un'azienda fallisce poiché questa
 * non rispetta il pattern o è nulla.
 */
public class PartitaIvaAziendaNonValidaException extends Exception {
  
  private static final long serialVersionUID = -4995833677028859753L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Partita IVA non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public PartitaIvaAziendaNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public PartitaIvaAziendaNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}
