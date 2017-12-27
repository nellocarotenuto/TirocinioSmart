package it.unisa.di.tirociniosmart.studenti;

import java.time.LocalDateTime;

public class RichiestaIscrizione {

  /**
   * Costruisce un oggetto RichiestaIscrizione vuoto.
   * <b>Questo costruttore non dev'essere mai utilizzato</b>, è presente solo per esigenze del
   * container.
   */
  RichiestaIscrizione() {
  }
  
  /**
   * Costruisce un oggetto RichiestaIscrizione vuoto che dev'essere popolato tramite i metodi
   * setters.
   * Questo costruttore ha visibilità di pacchetto e non può essere utilizzato all'esterno. Istanze
   * di questa classe si possono ottenere solo tramite {@link Studente#getRichiestaIscrizione()}.
   */
  RichiestaIscrizione(Studente studente) {
    this.studente = studente;
  }
  
  /**
   * Permette di ottenere l'identificatore della richiesta d'iscrizione.
   * 
   * @return Long che rappresenta l'identificatore della richiesta d'iscrizione
   */
  public long getId() {
    return id;
  }
  
  /**
   * Permette di ottenere lo stato della richiesta d'iscrizione.
   * 
   * @return {@link RichiestaIscrizione#IN_ATTESA} se la richiesta è in attesa,
   *         {@link RichiestaIscrizione#APPROVATA} se la richiesta è approvata,
   *         {@link RichiestaIscrizione#RIFIUTATA} se la richiesta è rifiutata
   */
  public int getStatus() {
    return status;
  }
  
  /**
   * Permette di specificare lo stato della richiesta d'iscrizione.
   * 
   * @param status Intero che rappresenta lo stato che si vuole assegnare alla richiesta
   *               d'iscrizione
   *               
   * @pre status = {@link RichiestaIscrizione#IN_ATTESA} or
   *      status = {@link RichiestaIscrizione#APPROVATA} or
   *      status = {@link RichiestaIscrizione#RIFIUTATA}
   *      
   * @post getStatus() = status
   */
  public void setStatus(int status) {
    this.status = status;
  }
  
  /**
   * Permette di ottenere la data e l'ora in cui è stata inviata la richiesta d'iscrizione.
   * 
   * @return La data e l'ora in cui è stata inviata la richiesta d'iscrizione
   */
  public LocalDateTime getDataRichiesta() {
    return dataRichiesta;
  }
  
  /**
   * Permette di specificare la data e l'ora in cui è stata inviata la richiesta d'iscrizione.
   *  
   * @param dataRichiesta Oggetto LocalDateTime che rappresenta la data e l'ora in cui è stata
   *                      inviata la richiesta d'iscrizione.
   * 
   * @pre dataRichiesta != null
   * 
   * @post getDataRichiesta().equals(dataRichiesta)
   */
  public void setDataRichiesta(LocalDateTime dataRichiesta) {
    this.dataRichiesta = dataRichiesta;
  }
  
  /**
   * Permette di ottenere lo studente che ha inviato la richiesta d'iscrizione.
   * 
   * @return L'oggetto {@link Studente} che rappresenta lo studente che ha inviato la richiesta
   *         d'iscrizione
   */
  public Studente getStudente() {
    return studente;
  }
  
  private long id;
  private int status;
  private LocalDateTime dataRichiesta;
  private Studente studente;
  
  /**
   * Costante che rappresenta lo stato "in attesa" di una richiesta d'iscrizione.
   * Una richiesta si trova in questo stato quando non è ancora stata gestita dall'ufficio tirocini.
   */
  public static final int IN_ATTESA = 0;
  
  /**
   * Costante che rappresenta lo stato "approvato" di una richiesta d'iscrizione.
   * Una richiesta si trova in questo stato quando è stata esaminata ed approvata dall'ufficio
   * tirocini.
   */
  public static final int APPROVATA = 1;
  
  /**
   * Costante che rappresenta lo stato "rifiutato" di una richiesta d'iscrizione.
   * Una richiesta si trova in questo stato quando è stata esaminata e rifiutata dall'ufficio
   * tirocini.
   */
  public static final int RIFIUTATA = 2;

}
