package it.unisa.di.tirociniosmart.studenti;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

/**
 * Classe che modella una richiesta d'iscrizione inviata da uno studente.
 * <b>Questa classe non può essere istanziata dall'esterno ma sue istanze possono essere ottenute
 * solo tramite {@link Studente#getRichiestaIscrizione()}.</b>
 * 
 * @see Studente
 */
@Entity
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
   * Permette di determinare se due oggetti rappresentano la stessa richiesta d'iscrizione sulla
   * base dell'identificatore.
   */
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (getClass() != object.getClass()) {
      return false;
    }
    
    RichiestaIscrizione richiestaIscrizione = (RichiestaIscrizione) object;
    
    return id == richiestaIscrizione.getId();
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto RichiestaIscrizione.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "RichiestaIscrizione [id=" + id + ", status=" + status + ", dataRichiesta="
        + dataRichiesta + ", studente=" + studente.getUsername() + "]";
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
  
  /**
   * Permette di ottenere il commento alla richiesta fatto dall'ufficio tirocini.
   *   
   * @return La stringa che rappresenta il commento alla richiesta da parte dell'ufficio tirocini
   */
  public String getCommentoUfficioTirocini() {
    return commentoUfficioTirocini;
  }

  /**
   * Permette di specificare il commento alla richiesta da parte dell'ufficio tirocini.
   * 
   * @param commentoUfficioTirocini Stringa che rappresenta il commento che si vuole assegnare
   *                                alla richiesta
   *                                
   * @pre commentoUfficioTirocini != null
   * 
   * @post getCommentoUfficioTirocini().equals(commentoUfficioTirocini)
   */
  public void setCommentoUfficioTirocini(String commentoUfficioTirocini) {
    this.commentoUfficioTirocini = commentoUfficioTirocini;
  }
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private int status;
  private LocalDateTime dataRichiesta;
  
  @Lob
  private String commentoUfficioTirocini;
  
  @OneToOne(mappedBy = "richiestaIscrizione")
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
